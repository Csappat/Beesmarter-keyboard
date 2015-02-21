package com.csappat.pre.biometrickeyboardid.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class XMLParser {

    public static List<PatternModel> parseXML(String str) throws Exception {
        ArrayList<PatternModel> models = new ArrayList<PatternModel>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader(str));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End document");
            } else if(eventType == XmlPullParser.START_TAG) {
                try {
                    int relposx = Integer.parseInt(xpp.getAttributeValue(null, "relative-pos-x").replace("%", ""));
                    int relposy = Integer.parseInt(xpp.getAttributeValue(null, "relative-pos-y").replace("%", ""));


                    if (xpp.getName().equals("key-down")) {
                        PatternModel model = new PatternModel(Type.KeyDown,
                                Integer.parseInt(xpp.getAttributeValue(null, "posix-time")),
                                xpp.getAttributeValue(null, "code").charAt(0),
                                relposx,
                                relposy);
                        models.add(model);
                    } else {
                        PatternModel model = new PatternModel(Type.KeyRelease,
                                Integer.parseInt(xpp.getAttributeValue(null, "posix-time")),
                                xpp.getAttributeValue(null, "code").charAt(0),
                                relposx,
                                relposy);
                        models.add(model);
                    }
                    Log.d("XMLParser", ""+relposx + " " + relposy + " " + Integer.parseInt(xpp.getAttributeValue(null, "posix-time")) + xpp.getAttributeValue(null, "code").charAt(0) );
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.d("XMLParser", "No attr.");
                }
                System.out.println("Start tag "+xpp.getName());
            } else if(eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag "+xpp.getName());
            } else if(eventType == XmlPullParser.TEXT) {
                System.out.println("Text "+xpp.getText());

            }

            eventType = xpp.next();
        }
        Log.d("XMLParser", models.toString());
        return models;
    }


}
