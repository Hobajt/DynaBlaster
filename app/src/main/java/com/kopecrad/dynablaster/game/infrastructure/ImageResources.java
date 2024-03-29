package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.ObjectFactory;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;
import com.kopecrad.dynablaster.game.objects.graphics.PlayerAnimation;
import com.kopecrad.dynablaster.game.objects.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages textures and their distribution.
 */
public class ImageResources {

    public static final int TEXTURE_DEFAULT= R.drawable.no_texture;

    private String packageName;
    private Resources res;

    private Map<String, Texture> textures;
    private Map<String, Animation> anims;
    private PlayerAnimation plAnim;

    public ImageResources(Context context) {
        this.res= context.getResources();
        packageName= context.getPackageName();

        textures= new HashMap<>();
        anims= new HashMap<>();

        ObjectFactory.setResourceRef(this);
        GameObject.setImageResources(this);
        Animation.setResourceRef(this);
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

    public Animation getAnim(String identifier) {
        if(!anims.containsKey(identifier)) {
            int sheetID= fetchDrawable(identifier);
            Animation a= new Animation(identifier, BitmapFactory.decodeResource(res, sheetID), sheetID);
            anims.put(identifier, a);
        }
        return anims.get(identifier);
    }

    public ObjectGraphics getGraphics(String identifier) {
        if(identifier.endsWith("_anim"))
            return getAnim(identifier);
        else
            return getTexture(identifier);
    }

    /**
     * Attempts to load drawable resource id with provided string name.
     */
    private int fetchDrawable(String identifier) {
        int id= res.getIdentifier(identifier, "drawable", packageName);
        if(id != 0)
            return id;
        return TEXTURE_DEFAULT;
    }

    public void updateAnimationFrames() {
        for(Animation a : anims.values()) {
            a.update();
        }
    }

    public ObjectGraphics getPlayerAnim() {
        if(plAnim == null)
            plAnim= assemblePlayerAnim();
        return plAnim;
    }

    private PlayerAnimation assemblePlayerAnim() {
        Map<Integer, Animation> playerAnims= new HashMap<>();

        Point[] pts= new Point[] {
                new Point(-1,0),
                new Point(1,0),
                new Point(0,-1),
                new Point(0,1),
                new Point(0,0),
        };

        for(Point p : pts) {
            int val= (2+p.x) + 10* (p.y+2);
            playerAnims.put(val, getAnim("player_" + Integer.toString(val) + "_anim"));
        }

        return new PlayerAnimation(playerAnims);
    }
}
