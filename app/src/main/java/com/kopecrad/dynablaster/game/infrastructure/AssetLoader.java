package com.kopecrad.dynablaster.game.infrastructure;

import android.content.res.AssetManager;

import com.kopecrad.dynablaster.game.objects.graphics.spritesheet.SpritesheetData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

public abstract class AssetLoader {

    protected static AssetManager manager;

    public static void setManager(AssetManager manager) {
        AssetLoader.manager = manager;
    }

    /**
     * Skips content of the xml element
     */
    protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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
