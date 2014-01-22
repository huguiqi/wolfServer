 

package com.wolf.passbook.domain.signing;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.wolf.passbook.domain.PKPass;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableFile;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.map.util.ISO8601DateFormat;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public final class PKSigningUtil {

    private static final int ZIP_BUFFER_SIZE = 8192;
    private static final String MANIFEST_JSON_FILE_NAME = "manifest.json";
    private static final String PASS_JSON_FILE_NAME = "pass.json";

    private PKSigningUtil() {
    }

    public static byte[] createSignedAndZippedPkPassArchive(final PKPass pass, final String pathToTemplateDirectory,
            final PKSigningInformation signingInformation) throws Exception {

        File tempPassDir = Files.createTempDir();
        FileUtils.copyDirectory(new File(pathToTemplateDirectory), tempPassDir);

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonObjectMapper.setDateFormat(new ISO8601DateFormat());
        
        createPassJSONFile(pass, tempPassDir, jsonObjectMapper);

        File manifestJSONFile = createManifestJSONFile(tempPassDir, jsonObjectMapper);

        signManifestFile(tempPassDir, manifestJSONFile, signingInformation);

        byte[] zippedPass = createZippedPassAndReturnAsByteArray(tempPassDir);

        FileUtils.deleteDirectory(tempPassDir);
        return zippedPass;
    }

    public static void signManifestFile(final File temporaryPassDirectory, final File manifestJSONFile,
            final PKSigningInformation signingInformation) throws Exception {

        if (temporaryPassDirectory == null || manifestJSONFile == null || signingInformation == null || !signingInformation.isValid()) {
            throw new IllegalArgumentException("Null params are not supported");
        }
        addBCProvider();

        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA1withRSA").setProvider(BouncyCastleProvider.PROVIDER_NAME).build(
                signingInformation.getSigningPrivateKey());

        generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider(
                BouncyCastleProvider.PROVIDER_NAME).build()).build(sha1Signer, signingInformation.getSigningCert()));

        List<X509Certificate> certList = new ArrayList<X509Certificate>();
        certList.add(signingInformation.getAppleWWDRCACert());
        certList.add(signingInformation.getSigningCert());

        Store certs = new JcaCertStore(certList);

        generator.addCertificates(certs);

        CMSSignedData sigData = generator.generate(new CMSProcessableFile(manifestJSONFile), false);
        byte[] signedDataBytes = sigData.getEncoded();

        File signatureFile = new File(temporaryPassDirectory.getAbsolutePath() + File.separator + "signature");
        FileOutputStream signatureOutputStream = new FileOutputStream(signatureFile);
        signatureOutputStream.write(signedDataBytes);
        signatureOutputStream.close();
    }

    public static PKSigningInformation loadSigningInformationFromPKCS12FileAndIntermediateCertificateFile(final String pkcs12KeyStoreFilePath,
            final String keyStorePassword, final String appleWWDRCAFilePath) throws IOException, NoSuchAlgorithmException, CertificateException,
            KeyStoreException, NoSuchProviderException, UnrecoverableKeyException {
        addBCProvider();

        KeyStore pkcs12KeyStore = loadPKCS12File(pkcs12KeyStoreFilePath, keyStorePassword);
        Enumeration<String> aliases = pkcs12KeyStore.aliases();

        PrivateKey signingPrivateKey = null;
        X509Certificate signingCert = null;

        while (aliases.hasMoreElements()) {
            String aliasName = aliases.nextElement();

            Key key = pkcs12KeyStore.getKey(aliasName, keyStorePassword.toCharArray());
           
            if (key instanceof PrivateKey) {
                signingPrivateKey = (PrivateKey) key;
                Object cert = pkcs12KeyStore.getCertificate(aliasName);
                if (cert instanceof X509Certificate) {
                    signingCert = (X509Certificate) cert;
                    break;
                }
            }
        }

        X509Certificate appleWWDRCACert = loadDERCertificate(appleWWDRCAFilePath);
        if (signingCert == null || signingPrivateKey == null || appleWWDRCACert == null) {
            throw new IOException("Couldn#t load all the neccessary certificates/keys");
        }

        return new PKSigningInformation(signingCert, signingPrivateKey, appleWWDRCACert);
    }

    public static KeyStore loadPKCS12File(final String filePath, final String password) throws IOException, NoSuchAlgorithmException,
            CertificateException, KeyStoreException, NoSuchProviderException {
        addBCProvider();
        KeyStore keystore = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);

        keystore.load(new FileInputStream(filePath), password.toCharArray());
        return keystore;
    }

    public static X509Certificate loadDERCertificate(final String filePath) throws IOException, CertificateException {
        FileInputStream certificateFileInputStream = null;
        try {
            certificateFileInputStream = new FileInputStream(filePath);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            Certificate certificate = certificateFactory.generateCertificate(certificateFileInputStream);
            if (certificate instanceof X509Certificate) {
                return (X509Certificate) certificate;
            }
            throw new IOException("The key from '" + filePath + "' could not be decrypted");
        } catch (IOException ex) {
            throw new IOException("The key from '" + filePath + "' could not be decrypted", ex);
        } catch (NoSuchProviderException ex) {
            throw new IOException("The key from '" + filePath + "' could not be decrypted", ex);
        } finally {
            IOUtils.closeQuietly(certificateFileInputStream);
        }
    }

    private static void addBCProvider() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

    }

    private static void createPassJSONFile(final PKPass pass, final File tempPassDir, final ObjectMapper jsonObjectMapper) throws IOException,
            JsonGenerationException, JsonMappingException {
        File passJSONFile = new File(tempPassDir.getAbsolutePath() + File.separator + PASS_JSON_FILE_NAME);

        FilterProvider filters = new SimpleFilterProvider().addFilter("pkPassFilter", SimpleBeanPropertyFilter.serializeAllExcept("valid",
                "validationErrors", "foregroundColorAsObject", "backgroundColorAsObject", "labelColorAsObject"));
        jsonObjectMapper.setSerializationInclusion(Inclusion.NON_NULL);
        jsonObjectMapper.getSerializationConfig().addMixInAnnotations(Object.class, PropertyFilterMixIn.class);

        ObjectWriter objectWriter = jsonObjectMapper.writer(filters);
        objectWriter.writeValue(passJSONFile, pass);
    }

    private static File createManifestJSONFile(final File tempPassDir, final ObjectMapper jsonObjectMapper) throws IOException,
            JsonGenerationException, JsonMappingException {
        Map<String, String> fileWithHashMap = new HashMap<String, String>();

        HashFunction hashFunction = Hashing.sha1();
        File[] filesInTempDir = tempPassDir.listFiles();
        hashFilesInDirectory(filesInTempDir, fileWithHashMap, tempPassDir.getAbsolutePath() + File.separator, hashFunction);
        File manifestJSONFile = new File(tempPassDir.getAbsolutePath() + File.separator + MANIFEST_JSON_FILE_NAME);
        jsonObjectMapper.writeValue(manifestJSONFile, fileWithHashMap);
        return manifestJSONFile;
    }

    private static void hashFilesInDirectory(final File[] files, final Map<String, String> fileWithHashMap, final String workingDirectory,
            final HashFunction hashFunction) throws IOException {
        for (File passResourceFile : files) {
            if (passResourceFile.isFile()) {
                HashCode hash = Files.hash(passResourceFile, hashFunction);
                String name = passResourceFile.getAbsolutePath().replace(workingDirectory, "");
                fileWithHashMap.put(name, Hex.encodeHexString(hash.asBytes()));
            } else if (passResourceFile.isDirectory()) {
                hashFilesInDirectory(passResourceFile.listFiles(), fileWithHashMap, workingDirectory, hashFunction);
            }
        }
    }

    private static byte[] createZippedPassAndReturnAsByteArray(final File tempPassDir) throws IOException {
        ByteArrayOutputStream byteArrayOutputStreamForZippedPass = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStreamForZippedPass);
        zip(tempPassDir, tempPassDir, zipOutputStream);
        zipOutputStream.close();
        return byteArrayOutputStreamForZippedPass.toByteArray();
    }

    private static final void zip(final File directory, final File base, final ZipOutputStream zipOutputStream) throws IOException {
        File[] files = directory.listFiles();
        byte[] buffer = new byte[ZIP_BUFFER_SIZE];
        int read = 0;
        for (int i = 0, n = files.length; i < n; i++) {
            if (files[i].isDirectory()) {
                zip(files[i], base, zipOutputStream);
            } else {
                FileInputStream fileInputStream = new FileInputStream(files[i]);
                ZipEntry entry = new ZipEntry(getRelativePathOfZipEntry(files[i], base));
                zipOutputStream.putNextEntry(entry);
                while (-1 != (read = fileInputStream.read(buffer))) {
                    zipOutputStream.write(buffer, 0, read);
                }
                fileInputStream.close();
            }
        }
    }

    private static String getRelativePathOfZipEntry(final File fileToZip, final File base) {
        String relativePathOfFile = fileToZip.getPath().substring(base.getPath().length() + 1);
        if (File.separatorChar != '/') {
            relativePathOfFile = relativePathOfFile.replace(File.separatorChar, '/');
        }

        return relativePathOfFile;
    }

    @JsonFilter("pkPassFilter")
    class PropertyFilterMixIn {
        // just a dummy
    }
}
