package com.wolf.listener;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUitl {

    private static final int BUFFER = 1024;

    public static void unzip(InputStream inputStream, String path) throws IOException {
        File unzipFile = new File(path);
		unzipFile.mkdirs();
		ZipInputStream zip = new ZipInputStream(inputStream);
		ZipEntry entry = null;
		while ((entry = zip.getNextEntry()) != null) {
			String entryName = new String(entry.getName().getBytes("UTF-8"));
			if (entry.isDirectory()) {
				new File(unzipFile.getAbsolutePath() + "/" + entryName).mkdir();
			} else {
                createFile(unzipFile.getAbsolutePath() + "/", zip, entryName);
			}
		}
		zip.close();
		inputStream.close();
    }

    private static void createFile(String path, ZipInputStream zip, String entryName) throws IOException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(new File(
                    path + entryName));
            byte[] buffer = new byte[BUFFER * 8];
            int readLen = 0;
            while ((readLen = zip.read(buffer, 0, BUFFER * 8)) != -1) {
                output.write(buffer, 0, readLen);
            }
        } finally {
            if (output != null) {
                output.flush();
                output.close();
            }
        }
    }
}
