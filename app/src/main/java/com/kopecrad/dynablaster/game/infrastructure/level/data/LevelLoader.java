package com.kopecrad.dynablaster.game.infrastructure.level.data;

import android.content.res.AssetManager;
import android.graphics.Point;
import android.util.Log;
import android.util.Xml;

import com.kopecrad.dynablaster.game.infrastructure.AssetLoader;
import com.kopecrad.dynablaster.game.infrastructure.EnemyData;
import com.kopecrad.dynablaster.game.infrastructure.level.WinConditions;
import com.kopecrad.dynablaster.game.objects.tile.TilesetType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages level loading and parsing from XML to LevelData object.
 */
public class LevelLoader extends AssetLoader {
    public static final String namespace= null;

    public LevelData loadLevel(int levelID) {
        String filename= "levels/level" + levelID + ".xml";
        Log.d("loadXML", "--Loading LevelData xml - " + filename);

        try {
            InputStream in = manager.open(filename);
            return parse(in, levelID);
        } catch (XmlPullParserException e) {
            Log.d("loadXML", "loadLevel:: Failed during parsing");
        } catch (IOException e) {
            Log.d("loadXML", "loadLevel:: Failed to open level data - " + filename);
        }
        return null;
    }

    private LevelData parse(InputStream in, int levelID) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser= Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readData(parser, levelID);
        } finally {
            in.close();
        }
    }

    /**
     * Reads and parses level data from xml file.
     */
    private LevelData readData(XmlPullParser parser, int levelID) throws IOException, XmlPullParserException {
        LevelData data = new LevelData(levelID);

        parser.require(XmlPullParser.START_TAG, namespace, "level");
        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();

            switch (name) {
                case "description":
                    if(!loadDescription(data, parser)) {
                        Log.d("loadXML", "LoadLevel:: failed to load description");
                        return null;
                    }
                    Log.d("loadXML", "LoadLevel:: Description loaded");
                    break;
                case "settings":
                    if(!loadSettings(data, parser)) {
                        Log.d("loadXML", "LoadLevel:: failed to load settings");
                        return null;
                    }
                    Log.d("loadXML", "LoadLevel:: Settings loaded");
                    break;
                case "map":
                    if(!loadMap(data, parser)) {
                        Log.d("loadXML", "LoadLevel:: failed to load map");
                        return null;
                    }
                    Log.d("loadXML", "LoadLevel:: Map loaded");
                    break;
                case "enemies":
                    if(!loadEnemies(data, parser)) {
                        Log.d("loadXML", "LoadLevel:: failed to load enemies");
                        return null;
                    }
                    Log.d("loadXML", "LoadLevel:: Enemies loaded");
                    break;
                default:
                    parser.next();
            }
        }

        Log.d("loadXML", "LoadLevel:: Level data loading finished.");
        return data;
    }

    private boolean loadEnemies(LevelData data, XmlPullParser parser) {
        List<EnemyData> ed= new ArrayList<>();

        try {
            parser.require(XmlPullParser.START_TAG, namespace, "enemies");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                    continue;

                if(!parser.getName().equals("enemy"))
                    continue;

                readEnemy(ed, parser);
            }

            data.enemies= ed;
            return true;

        } catch (IOException | XmlPullParserException e) {}
        return false;
    }

    private boolean readEnemy(List<EnemyData> enemies, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, "enemy");

        Integer id= null;
        Integer count= null;

        try {
            for (int i = 0; i < parser.getAttributeCount(); i++) {
                String attName = parser.getAttributeName(i);
                switch (attName) {
                    case "id":
                        id = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "count":
                        count = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                }
            }

            if (id == null)
                return false;

            if (count == null)
                count = 1;

            enemies.add(new EnemyData(count, id));
            parser.next();
            return true;
        } catch (NumberFormatException e) {}
        return false;
    }

    /**
     * Loads setttings tag from xml
     */
    private boolean loadSettings(LevelData data, XmlPullParser parser) {
        try {
            parser.require(XmlPullParser.START_TAG, namespace, "settings");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                    continue;

                String name = parser.getName();
                switch (name) {
                    case "playerSpawn":
                        if (!loadSpawn(data, parser))
                            return false;
                        break;
                    case "tileset":
                        parser.require(XmlPullParser.START_TAG, namespace, "tileset");
                        data.tileset= TilesetType.GetByID(readText(parser));
                        parser.require(XmlPullParser.END_TAG, namespace, "tileset");
                        break;
                    case "winConditions":
                        if (!loadWinConditions(data, parser))
                            return false;
                        break;
                }
            }
        } catch (IOException | XmlPullParserException e) {
            return false;
        }

        /*Log.d("loadXML", "Spawn: (" + data.playerSpawn.x + ", " + data.playerSpawn.y + ")");
        Log.d("loadXML", "Tileset: " + data.tilesetPostfix);
        Log.d("loadXML", "Conds: " + data.conditions.toString());*/
        return true;
    }

    private boolean loadWinConditions(LevelData data, XmlPullParser parser) {
        try {
            parser.require(XmlPullParser.START_TAG, namespace, "winConditions");

            int goal = -1, timeLimit = -1;
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                    continue;

                String name = parser.getName();
                try {
                    switch (name) {
                        case "goal":
                            parser.require(XmlPullParser.START_TAG, namespace, "goal");
                            goal = Integer.parseInt(readText(parser));
                            parser.require(XmlPullParser.END_TAG, namespace, "goal");
                            break;
                        case "timeLimit":
                            parser.require(XmlPullParser.START_TAG, namespace, "timeLimit");
                            timeLimit = Integer.parseInt(readText(parser));
                            parser.require(XmlPullParser.END_TAG, namespace, "timeLimit");
                            break;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            try {
                WinConditions.WinGoal goalEnum= WinConditions.WinGoal.values()[goal];
                data.conditions= new WinConditions(timeLimit, goalEnum);
            } catch(Exception e) {
                return false;
            }

        } catch (IOException | XmlPullParserException e) {
            return false;
        }
        return true;
    }

    private boolean loadSpawn(LevelData data, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, "playerSpawn");

        int x= 0, y= 0;
        try {
            for (int i = 0; i < parser.getAttributeCount(); i++) {

                String attName = parser.getAttributeName(i);
                switch (attName) {
                    case "x":
                        x = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "y":
                        y = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        data.playerSpawn= new Point(x,y);
        parser.next();
        return true;
    }

    /**
     * Loads map tag from xml
     */
    private boolean loadMap(LevelData data, XmlPullParser parser) {
        try {
            parser.require(XmlPullParser.START_TAG, namespace, "map");

            List<String> rows = new ArrayList<>();
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                    continue;

                String name = parser.getName();
                if (!name.equals("row"))
                    continue;

                parser.require(XmlPullParser.START_TAG, namespace, "row");
                rows.add(readText(parser));
                parser.require(XmlPullParser.END_TAG, namespace, "row");
            }
            if (rows.size() < 1)
                return false;

            data.setupMap(rows);
        } catch (IOException | XmlPullParserException e) {
            return false;
        }
        return true;
    }

    /**
     * Loads and validates level description tag.
     * @Return Returns false when loading fails.
     */
    private boolean loadDescription(LevelData data, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, "description");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            switch (name) {
                case "id":
                    parser.require(XmlPullParser.START_TAG, namespace, "id");
                    if(Integer.parseInt(readText(parser)) != data.id)
                        return false;
                    parser.require(XmlPullParser.END_TAG, namespace, "id");
                    break;
                case "name":
                    parser.require(XmlPullParser.START_TAG, namespace, "name");
                    data.name= readText(parser);
                    parser.require(XmlPullParser.END_TAG, namespace, "name");
                    break;
                default:
                    skip(parser);
            }
        }
        return true;
    }

    /**
     * Reads xml element's text
     */
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
