package com.hieuvt.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hieuvt on 07/10/2015.
 */
public class SimpleHttpClient {

    public void requestURL(String url) {
        try {
            URL destUrl = new URL(url);
            URLConnection conn = destUrl.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
        simpleHttpClient.requestURL("http://vnexpress.net/");
    }
}
