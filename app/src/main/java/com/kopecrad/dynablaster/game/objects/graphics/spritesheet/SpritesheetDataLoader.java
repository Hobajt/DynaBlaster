package com.kopecrad.dynablaster.game.objects.graphics.spritesheet;

import android.util.Log;
import android.util.Xml;

import com.kopecrad.dynablaster.game.infrastructure.AssetLoader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages spritesheet details loading from xml.
 */
public class SpritesheetDataLoader extends AssetLoader {

    private static final String FILENAME = "spritesheet_data.xml";
    private final String namespace = null;

    public Map<String,SpritesheetData> loadData() {
        try {
            InputStream in = manager.open(FILENAME);
            return parse(in);
        } catch (XmlPullParserException e) {
            Log.d("spriteXML", "SpriteXML:: error during parsing");
        }catch(IOException e) {
            Log.d("spriteXML", "SpriteXML:: error during loading");
        }

        return null;
    }

    private Map<String,SpritesheetData> parse(InputStream in) throws IOException, XmlPullParserException {
        Map<String,SpritesheetData> data= new HashMap<>();

        try {
            XmlPullParser parser= Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            parser.require(XmlPullParser.START_TAG, namespace, "data");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                String name = parser.getName();
                Log.d("spriteXML", "Name: " + name);
                // Starts by looking for the entry tag
                if (name.equals("sheet")) {
                    if(!readData(parser, data)) {
                        Log.d("spriteXML", "Loading failed");
                        return null;
                    }
                } else {
                    skip(parser);
                }
            }
        } finally {
            in.close();
        }

        Log.d("spriteXML", "Loading succeeded");
        return data;
    }

    private boolean readData(XmlPullParser parser, Map<String,SpritesheetData> data) {
        try {
            parser.require(XmlPullParser.START_TAG, namespace, "sheet");

            String name = null;
            Integer colCount = null;
            Integer rowCount = null;
            Integer count= null;
            Integer speed = null;
            String ghost= null;

            for (int i = 0; i < parser.getAttributeCount(); i++) {
                String attName = parser.getAttributeName(i);
                switch (attName) {
                    case "name":
                        name = parser.getAttributeValue(i);
                        break;
                    case "colCount":
                        colCount = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "rowCount":
                        rowCount = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "count":
                        count = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "speed":
                        speed = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "ghost":
                        ghost = parser.getAttributeValue(i);
                        break;
                }
            }

            if(rowCount == null || colCount == null || name == null || count == null)
                return false;

            if(speed == null)
                speed= 100;

            data.put(name, new SpritesheetData(colCount, rowCount, count, speed, ghost));
            parser.next();
            return true;
        } catch (XmlPullParserException | IOException e) {
            Log.d("spriteXML", "Error during sheet data loading.");
            return false;
        } catch(NumberFormatException e) {
            Log.d("spriteXML", "Incorrect value read in sheet data");
            return false;
        }
    }
}
