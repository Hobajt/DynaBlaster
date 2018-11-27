package com.kopecrad.dynablaster.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;
import com.kopecrad.dynablaster.game.infrastructure.Scene;
import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

/**
 * Any object in the game, that can be visible.
 */
public class GameObject {

    private static ImageResources imgRes;
    private static ScreenSettings screen;
    private static Scene scene;

    private ObjectGraphics graphics;

    private Point position;
    private Rect boundingRect;

    protected GameObject(int x, int y, ObjectGraphics graphics) {
        setPosition(screen.calcPosition(x,y));
        this.graphics= graphics;
    }

    public GameObject(int x, int y, String graphics) {
        this(x, y, imgRes.getGraphics(graphics));
    }

    /**
     * Render call for this object.
     */
    public void render(Canvas canvas) {
        canvas.drawBitmap(graphics.getFrame(), null, screen.getScreenRect(boundingRect), null);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point p) {
        position= p;
        boundingRect= screen.getObjectRect(position);
    }

    public void addPosition(Point p) {
        setPosition(new Point(position.x + p.x, position.y + p.y));
    }

    public Rect getBoundingRect() {
        return boundingRect;
    }

    protected ScreenSettings getScreen() {
        return screen;
    }

    protected Bitmap getFrame() {
        return graphics.getFrame();
    }

    public static void setScreenSettings(ScreenSettings stg) {
        screen= stg;
    }

    public static void setImageResources(ImageResources res) {
        imgRes= res;
    }

    public static void setSceneRef(Scene s) {
        scene= s;
    }

    public boolean isTraversable() {
        return true;
    }

    protected Scene getScene() {
        return scene;
    }

    public Point getMapPos() {
        return getScreen().getClosestIndex(getPosition());
    }
}
