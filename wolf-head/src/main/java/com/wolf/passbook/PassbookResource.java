package com.wolf.passbook;



import com.wolf.common.utils.passbook.PKBarcode;
import com.wolf.common.utils.passbook.PKField;
import com.wolf.common.utils.passbook.PKLocation;
import com.wolf.common.utils.passbook.PKPass;
import com.wolf.common.utils.passbook.passes.PKStoreCard;
import com.wolf.common.utils.passbook.signing.PKSigningInformation;
import com.wolf.common.utils.passbook.signing.PKSigningUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/passbook")
@Component("passbookResource")
public class PassbookResource {


    @GET
    @Path("/getPassbook")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getPKPass()throws Exception{

         generenPKPass();
        return null;
    }



    private void generenPKPass() throws Exception{

        //		String dir = "D:/projects/pikato-trunk/PikatoPassBook/testFiles/";
        String dir = "/Users/sam/Desktop/test/";
        FileUtils.deleteQuietly(new File(dir + "my.pkpass"));
        PKPass pass = new PKPass();
//		pass.setAuthenticationToken("vxwxd7J8AlNNFPS8k0a0FfUFtq0ewzFdc");
        PKBarcode barcode = new PKBarcode();

        PKStoreCard storeCard = new PKStoreCard();
        List<PKField> headerFields = new ArrayList<PKField>();
        PKField headField = new PKField();
        headField.setKey("duration");
        headField.setLabel("3 晚");
        headField.setValue("2013-03-08 18:00");
        headerFields.add(headField);
        storeCard.setHeaderFields(headerFields);

        List<PKField> secondaryFields = new ArrayList<PKField>();
        PKField secondField = new PKField();
        secondField.setKey("guest");
        secondField.setLabel("客人:");
        secondField.setValue("胡小黑");

        PKField secondField1 = new PKField();
        secondField1.setKey("orderInfo");
        secondField1.setLabel("订单号:");
        secondField1.setValue("H11122222");

        secondaryFields.add(secondField);
        secondaryFields.add(secondField1);
        storeCard.setSecondaryFields(secondaryFields);

        List<PKField> backFields = new ArrayList<PKField>();
        PKField pkField1 = new PKField();
        pkField1.setKey("confirmation");
        pkField1.setLabel("Order Number");
        pkField1.setValue("H177036965");
        backFields.add(pkField1);

        PKField pkField2 = new PKField();
        pkField2.setKey("reservation");
        pkField2.setLabel("Reservation");
        pkField2.setValue("Check-in:\nJune 8, 2012, 3:00PM\nCheck-out:\nJune 11, 2012, 12:00PM");
        backFields.add(pkField2);

        PKField pkField3 = new PKField();
        pkField3.setKey("room");
        pkField3.setLabel("Room Type");
        pkField3.setValue("Fabulous Corner Room");
        backFields.add(pkField3);
        storeCard.setBackFields(backFields);

        pass.setStoreCard(storeCard);


//		barcode.setFormat(PKBarcodeFormat.PKBarcodeFormatQR);
//		barcode.setMessage("123456789");
//		barcode.setMessageEncoding(Charset.forName("utf-8"));
        //最重要的一块
        pass.setPassTypeIdentifier("pass.jinjiang.developertest");
        pass.setSerialNumber("003");
        pass.setTeamIdentifier("4Z78PCW342");
        pass.setBarcode(barcode);
        pass.setOrganizationName("Jin Jiang International E-Commerce Co., Ltd.");
        pass.setLogoText("锦江之星淮海中路世纪广场肯德基妹妹小黑店");
        pass.setLabelColor("rgb(255, 255, 255)");
        pass.setForegroundColor("rgb(255, 255, 255)");
        pass.setBackgroundColor("rgb(122,122,118)");
        pass.setDescription("W Hotel Reservation");
        pass.setFormatVersion(1);
        pass.setRelevantDate(new Date());

        List<PKLocation> locations = new ArrayList<PKLocation>();
        PKLocation location1 = new PKLocation();
        location1.setLatitude(37.6189722);
        location1.setLongitude(-122.3748889);
        locations.add(location1);

        PKLocation location2 = new PKLocation();
        location2.setLatitude(37.33182);
        location2.setLongitude(-122.03118);
        locations.add(location2);
        pass.setLocations(locations);
        PKSigningInformation pkSigningInformation = PKSigningUtil
                .loadSigningInformationFromPKCS12FileAndIntermediateCertificateFile(dir + "Cert.p12", "123456", dir
                        + "WWDR.pem");

        byte[] passZipAsByteArray = PKSigningUtil.createSignedAndZippedPkPassArchive(pass, dir + "hotelImg",
                pkSigningInformation);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(passZipAsByteArray);

        IOUtils.copy(inputStream, new FileOutputStream(dir + "my.pkpass"));

        System.out.println("======http://localhost:8086/wolf-head/"+dir+"my.pkpass");

    }

}
