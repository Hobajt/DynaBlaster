package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.objects.ObjectFactory;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;
import com.kopecrad.dynablaster.game.objects.graphics.Texture;
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

    private Map<String, Texture> textures;
    private Map<String, Animation> anims;

    public ImageResources(Context context) {
        this.res= context.getResources();
        packageName= context.getPackageName();

        textures= new HashMap<>();
        anims= new HashMap<>();

        ObjectFactory.setResourceRef(this);
    }

    /**
     * Fetches given texture.
     */
    public Texture getTexture(String identifier) {
        if(!textures.containsKey(identifier)) {
            Texture t= new Texture(BitmapFactory.decodeResource(res, fetchDrawable(identifier)));
            textures.put(identifier, t);
        }
        return textures.get(identifier);
    }

    private int fetchDrawable(String identifier) {
        int id= res.getIdentifier(identifier, "drawable", packageName);
        if(id != 0)
            return id;
        return TEXTURE_DEFAULT;
    }

    public Animation getAnim(String identifier) {
        if(!anims.containsKey(identifier)) {
            //TODO: actually load !!
            Animation a= new Animation(
                    new Bitmap[] {BitmapFactory.decodeResource(res, fetchDrawable(identifier))}
            );
            anims.put(identifier, a);
        }
        return anims.get(identifier);
    }
}
