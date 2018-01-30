package com.example.dim.pla01;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by dim on 17/12/2017.
 */

public class RestRequest {

    String uri;
    public RestRequest() {
    }

    public RestRequest(String uri){
        this.uri = uri;
    }

    public void runRequest() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here

                // Create URL
                String url = "http://10.0.2.2:8080/maven_jws/webresources/com.mycompany.maven_jws.entities.person/test";
               // String url = uri;
                        URL githubEndpoint = null;
                try {
                    //http://desktop-bcvrv9o:8080/maven_jws/
                    //http://localhost:8080/maven_jws/webresources/com.mycompany.maven_jws.entities.person
                    //http://localhost:8080/maven_jws/webresources/com.mycompany.maven_jws.entities.person/%7Bid%7D
                    githubEndpoint = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                //HttpURLConnection myConnection;
                //HttpGet get;
                HttpClient httpClient = new DefaultHttpClient();

                //  myConnection = (HttpURLConnection) githubEndpoint.openConnection();
                // myConnection.setRequestMethod("GET");
                //myConnection.setDoOutput(true);
                // myConnection.setRequestProperty("Accept", "application/jason");

                InputStreamReader inStreamR = null;
                try {
                    HttpGet request = new HttpGet(url);
                    request.addHeader("accept", "application/json");
                    // request.addHeader("accept", "text/html");
                   // request.addHeader("accept", "text/plain");
                    HttpResponse response = httpClient.execute(request);
                    System.out.println("Response : "+response.toString());
                    for (Header h : response.getAllHeaders()) {
                        System.out.println("Header name : "+h.getName()+" - value : "+h.getValue());
                    }
                    //System.out.println("Response : "+);
                    HttpEntity entity = response.getEntity();
                    System.out.println("Encoding : "+entity.getContentEncoding());
                    System.out.println("Type : "+entity.getContentType());
                    System.out.println("Length : "+entity.getContentLength());
                    System.out.println("Content : "+entity.getContent());
                    InputStream inStream = entity.getContent();
                    inStreamR = new InputStreamReader(inStream);
  /*
  //---- this code works fine, if server does not return json !
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(inStreamR);

                    for (String s = br.readLine(); s!= null; s = br.readLine()) {
                        sb.append(s).append("\n");
                    }

                    System.out.println(sb.toString());
                    inStream.close();
                */

                    // code working for json
                    JsonReader jsonReader = new JsonReader(inStreamR);
                    //jsonReader.setLenient(true); // if json malformed
                    jsonReader.beginObject(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys
                        // if (key == "someText") {
                        String key = jsonReader.nextName(); // Fetch the next key
                        String value = jsonReader.nextString();
                        System.out.println("key : " + key + "  -  value : " + value);
                        break;
                        // } else {
                        //   jsonReader.skipValue(); // Skip values of other keys
                        //}
                    }

                    jsonReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

