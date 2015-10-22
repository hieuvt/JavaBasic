package com.hieuvt.downloadclient;

import java.io.BufferedInputStream;import java.io.BufferedOutputStream;import java.io.File;import java.io.FileOutputStream;import java.io.IOException;import java.io.OutputStream;import java.lang.String;import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hieuvt on 14/10/2015.
 */
public class DownloadClient {


    public File download(String downloadLink, String downloadFolderStr) {

        String fileName = downloadLink.substring(downloadLink.lastIndexOf('/') + 1, downloadLink.length() );
        File saveFile = new File(downloadFolderStr + File.separator + fileName);

        try {

            File downloadFolder = new File(downloadFolderStr);
            if (!downloadFolder.exists()) {
                downloadFolder.mkdirs();
            }

            URL downloadURL = new URL(downloadLink);
            HttpURLConnection connDownload = (HttpURLConnection) downloadURL.openConnection();
            connDownload.connect();

            BufferedInputStream in = new BufferedInputStream(connDownload.getInputStream());

            OutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
            byte[] buf = new byte[256];
            int n = 0;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0 , n);
            }
            out.flush();
            out.close();

            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

            String downloadLink = "http://centos-hn.viettelidc.com.vn/7/isos/x86_64/CentOS-7-x86_64-Minimal-1503-01.iso";
            DownloadClient downloadClient = new DownloadClient();
        downloadClient.download(downloadLink, "download");
//            System.out.println(downloadClient.download(downloadLink).getAbsolutePath());
    }

}
