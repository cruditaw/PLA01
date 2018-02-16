package com.example.dim.pla01.managers;

import android.os.AsyncTask;
import android.util.Xml;

import com.example.dim.pla01.entities.CaveEntity;
import com.example.dim.pla01.entities.TypeVinEntity;
import com.example.dim.pla01.entities.UtilisateurEntity;
import com.example.dim.pla01.entities.VigneronEntity;
import com.example.dim.pla01.entities.VinEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    private SimpleDateFormat remoteFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    System.out.println(huc.getResponseCode() + " - " + huc.getResponseMessage());

                    InputStream is = new BufferedInputStream(huc.getInputStream());
                    List<CaveEntity> cavesList = parseCaves(is);

                    /*
                    int data = is.read();
                    // responseStr.append((char) data);
                    System.out.println("BEFORE LOOP " + (char) data);
                    while (data != -1) {
                        responseStr.append((char) data);
                        data = is.read();

                        System.out.println((char) data);
                    }
                    */
                    is.close();
                    huc.disconnect();
                    System.out.println("DISCONNECTED !");

                    System.out.println(responseStr.toString());
                    for (CaveEntity c :
                            cavesList) {
                        System.out.println(c.toString());
                    }

                    // check with local and merge !

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

    private final String ns = "";

    private List<CaveEntity> parseCaves(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }

    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<CaveEntity> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "caves");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("cave")) {
                entries.add(readCave(parser));
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "caves");
        return entries;
    }

    private CaveEntity readCave(XmlPullParser parser) throws XmlPullParserException, IOException {
        CaveEntity cave = new CaveEntity();

        parser.require(XmlPullParser.START_TAG, ns, "cave");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("cavId")) {
                cave.setCavId(readIntTag(parser, "cavId"));

            } else if (name.equals("cavNombreBouteilles")) {
                cave.setCavNombreBouteilles(readIntTag(parser, "cavNombreBouteilles"));

            } else if (name.equals("cavUtilisateur")) {
                cave.setCavUtilisateur(readCavUtilisateur(parser));

            } else if (name.equals("cavVin")) {
                cave.setCavVin(readCavVin(parser));

            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "cave");
        return cave;
    }

    private UtilisateurEntity readCavUtilisateur(XmlPullParser parser) throws IOException, XmlPullParserException {
        UtilisateurEntity utilisateur = new UtilisateurEntity();

        parser.require(XmlPullParser.START_TAG, ns, "cavUtilisateur");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("utrId")) {
                utilisateur.setUtrId(readIntTag(parser, "utrId"));

            } else if (name.equals("utrMail")) {
                utilisateur.setUtrMail(readTextTag(parser, "utrMail"));

            } else if (name.equals("utrNom")) {
                utilisateur.setUtrNom(readTextTag(parser, "utrNom"));

            } else if (name.equals("utrPrenom")) {
                utilisateur.setUtrPrenom(readTextTag(parser, "utrPrenom"));
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "cavUtilisateur");
        return utilisateur;
    }

    private VinEntity readCavVin(XmlPullParser parser) throws IOException, XmlPullParserException {
        VinEntity vin = new VinEntity();

        parser.require(XmlPullParser.START_TAG, ns, "cavVin");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("vinAnnee")) {
                vin.setVinAnnee(readIntTag(parser, "vinAnnee"));

            } else if (name.equals("vinDomaine")) {
                vin.setVinDomaine(readTextTag(parser, "vinDomaine"));

            } else if (name.equals("vinId")) {
                vin.setVinId(readIntTag(parser, "vinId"));

            } else if (name.equals("vinLibelle")) {
                vin.setVinLibelle(readTextTag(parser, "vinLibelle"));

            } else if (name.equals("vinMiseBouteille")) {
                vin.setVinMiseBouteille(readDateTag(parser, "vinMiseBouteille"));

            } else if (name.equals("vinType")) {
                vin.setVinType(readVinType(parser));

            } else if (name.equals("vinVigneron")) {
                vin.setVinVigneron(readVigneron(parser));
            } else {
                skip(parser);
            }
        }
       parser.require(XmlPullParser.END_TAG, ns, "cavVin");
        return vin;
    }

    private TypeVinEntity readVinType(XmlPullParser parser) throws IOException, XmlPullParserException {
        TypeVinEntity typevin = new TypeVinEntity();

        parser.require(XmlPullParser.START_TAG, ns, "vinType");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("tvinId")) {
                typevin.setTvinId(readIntTag(parser, "tvinId"));

            } else if (name.equals("tvinLibelle")) {
                typevin.setTvinLibelle(readTextTag(parser, "tvinLibelle"));
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "vinType");
        return typevin;
    }

    private VigneronEntity readVigneron(XmlPullParser parser) throws IOException, XmlPullParserException {
        VigneronEntity vigneron = new VigneronEntity();

        parser.require(XmlPullParser.START_TAG, ns, "vinVigneron");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("vignAdresse")) {
                vigneron.setVignAdresse(readTextTag(parser, "vignAdresse"));

            } else if (name.equals("vignCp")) {
                vigneron.setVignCp(readTextTag(parser, "vignCp"));

            } else if (name.equals("vignId")) {
                vigneron.setVignId(readIntTag(parser, "vignId"));

            } else if (name.equals("vignLibelle")) {
                vigneron.setVignLibelle(readTextTag(parser, "vignLibelle"));

            } else if (name.equals("vignMail")) {
                vigneron.setVignMail(readTextTag(parser, "vignMail"));

            } else if (name.equals("vignTel")) {
                vigneron.setVignTel(readTextTag(parser, "vignTel"));

            } else if (name.equals("vignVille")) {
                vigneron.setVignVille(readTextTag(parser, "vignVille"));

            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "vinVigneron");
        return vigneron;
    }

    private Date readDateTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        Date result = new Date();
        try {
            result = remoteFormat.parse(readText(parser));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private int readIntTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        int result = readInt(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private String readTextTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }


    private int readInt(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return Integer.valueOf(result);
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
