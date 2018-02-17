package com.example.dim.pla01.managers;

import android.os.AsyncTask;

import com.example.dim.pla01.entities.CaveEntity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by dim on 12/02/2018.
 */

public class DbSyncTest {
    private String urlStr; // useless ..
    private URL url;
    private StringBuilder responseStr;
    private HttpURLConnection huc;
    private XmlParserUtil xmlParserUtil;
    private List<CaveEntity> cavesList;

    private SimpleDateFormat remoteFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
   // private SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DbSyncTest() {
        responseStr = new StringBuilder();
    }

    public DbSyncTest(String urlStr) {
        this.urlStr = urlStr;
        responseStr = new StringBuilder();
        xmlParserUtil = new XmlParserUtil();
//        cavesList = new ArrayList<>();
    }

    public void showAfterSync() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (CaveEntity caveEntity : cavesList) {
                    System.out.println(caveEntity.toString());
                }
            }
        });
    }

    public void syncExtToLocal() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create connection
                try {
                    url = new URL(urlStr);
                    huc = (HttpURLConnection) url.openConnection();
                    System.out.println(huc.getResponseCode() + " - " + huc.getResponseMessage());

                    InputStream is = new BufferedInputStream(huc.getInputStream());
                    cavesList = xmlParserUtil.parseCaves(is);

                    is.close();
                    huc.disconnect();
                    System.out.println("DISCONNECTED !");

                   /* System.out.println(responseStr.toString());
                    for (CaveEntity c :
                            cavesList) {
                        System.out.println(c.toString());
                    }*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
