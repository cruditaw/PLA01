package com.example.dim.pla01.managers;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by dim on 12/02/2018.
 */

/* todo: Synchronize data if device is connected.
   todo: Find all data needed with REST API request to server.
   todo: what if action on deleted entry (client / server side) ? etc.. */
public class DbSyncTest {
    private String urlStr; // useless ..
    private URL url;
    private StringBuilder responseStr;
    private HttpURLConnection huc;


    public DbSyncTest() {
        responseStr = new StringBuilder();
    }

    public DbSyncTest(String urlStr) {
        this.urlStr = urlStr;
        responseStr = new StringBuilder();
    }

    public void syncExtToLocal() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create connection
                try {
                    url = new URL(urlStr);
                    huc = (HttpURLConnection) url.openConnection();
                    System.out.println(huc.getResponseCode()+" - "+huc.getResponseMessage());

                    InputStream is = new BufferedInputStream(huc.getInputStream());

                    int data = is.read();
                   // responseStr.append((char) data);
                    System.out.println("BEFORE LOOP " +(char)data);
                    while (data != -1) {
                        responseStr.append((char) data);
                        data = is.read();

                        System.out.println((char)data);
                    }

                    is.close();
                    huc.disconnect();
                    System.out.println("DISCONNECTED !");

                    System.out.println(responseStr.toString());


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

 

    public String readResponse() {
        return responseStr.toString();
    }
}
