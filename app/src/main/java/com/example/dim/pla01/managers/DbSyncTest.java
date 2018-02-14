package com.example.dim.pla01.managers;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by dim on 12/02/2018.
 */

/* todo: Synchronize data if device is connected.
   todo: Find all data needed with REST API request to server.
   todo: what if action on deleted entry (client / server side) ? etc.. */
public class DbSyncTest {
    String url;
    HttpEntity responseEntity;
    //String response;
    //String method;


    public DbSyncTest() {
    }

    public DbSyncTest(String url) {
        this.url = url;
    }

    public void syncExtToLocal() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create connection
                HttpClient httpClient = new DefaultHttpClient();
                InputStream inStream = null;

                try {
                    HttpGet request = new HttpGet(url);
                    request.addHeader("Accept", "application/xml");


                    HttpResponse response = httpClient.execute(request);

                    System.out.println("Headers : " + response.toString());
                    for (Header h : response.getAllHeaders()) {
                        System.out.println("Header name : " + h.getName() + " - value : " + h.getValue());
                    }
                    System.out.println("Response : " + response.toString());

                    responseEntity = response.getEntity();
                    System.out.println("Encoding : " + responseEntity.getContentEncoding());
                    System.out.println("Type : " + responseEntity.getContentType());
                    System.out.println("Length : " + responseEntity.getContentLength());
                    System.out.println("Content : " + responseEntity.getContent());

                    inStream = responseEntity.getContent();

                    InputStreamReader inStreamR = null;
                    inStreamR = new InputStreamReader(inStream);

                    // For json
                   // JsonReader jsonReader = new JsonReader(inStreamR);
                    //jsonReader.setLenient(true); // if json malformed ?

                    // jsonReader.beginObject();
                    //  while (jsonReader.hasNext()) {
                    //  String key = jsonReader.nextName();
                    //  String value = jsonReader.nextString();
                    //  System.out.println("key : " + key + "  -  value : " + value);
                    // }
                    // jsonReader.endObject();
                    // jsonReader.close();


                    // if not json, look 4 library !!

                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(inStreamR);

                    for (String s = br.readLine(); s!= null; s = br.readLine()) {
                        sb.append(s).append("\n");
                    }

                    System.out.println(sb.toString());
                    inStream.close();



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
