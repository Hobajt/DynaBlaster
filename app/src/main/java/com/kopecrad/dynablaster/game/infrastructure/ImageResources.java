package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.objects.tile.TileFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages textures and their distribution.
 */
public class ImageResources {

    public static final int TEXTURE_DEFAULT= R.drawable.no_texture;;

    private String packageName;
    private Resources res;

    //TODO: maybe replace Bitmap with some wrapper class to allow Animations
    private Map<String, Bitmap> textures;

    public ImageResources(Context context) {
        this.res= context.getResources();
        packageName= context.getPackageName();
        textures= new HashMap<>();

        TileFactory.setResourceRef(this);
    }

    /**
     * Fetches given texture.
     */
    public Bitmap getTexture(String identifier) {
        if(!textures.containsKey(identifier)) {
            Bitmap bmp = BitmapFactory.decodeResource(res, fetchDrawable(identifier));
            textures.put(identifier, bmp);
        }


        return textures.get(identifier);
    }

    private int fetchDrawable(String identifier) {
        int id= res.getIdentifier(identifier, "drawable", packageName);
        if(id != 0)
            return id;
        return TEXTURE_DEFAULT;
    }
}
