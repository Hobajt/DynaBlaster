package com.kopecrad.dynablaster.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;
import com.kopecrad.dynablaster.game.infrastructure.Scene;
import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

import java.util.logging.ConsoleHandler;

/**
 * Any object in the game, that can be visible.
 */
public class GameObject {

    private static ImageResources imgRes;
    private static ScreenSettings screen;
    private static Scene scene;

    protected static PlayerProgress progress;
    protected static LevelState state;

    private ObjectGraphics graphics;

    private Point position;
    protected Rect boundingRect;

    protected GameObject(int x, int y, ObjectGraphics graphics) {
        setPosition(screen.calcPosition(x,y));
        this.graphics= graphics;
    }

    public GameObject(int x, int y, String graphics) {
        this(x, y, imgRes.getGraphics(graphics));
    }

    public static void ehm() {
        imgRes.updateAnimationFrames();
    }

    /**
     * Render call for this object.
     */
    public void render(Canvas canvas) {
        canvas.drawBitmap(graphics.getFrame(), null, getScreenRect(), null);
    }

    protected Rect getScreenRect() {
        return screen.getScreenRect(boundingRect);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point p) {
        position= p;
        setupBoundingRect();
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

    protected void setupBoundingRect() {
        boundingRect= screen.getObjectRect(position);
    }

    public void setMapPosition(int x, int y) {
        setPosition(screen.calcPosition(x, y));
    }

    /**
     * Returns euclidean distance between this object's position and specified coords.
     */
    public float getDistanceFrom(int x, int y) {
        float f= (float)Math.sqrt((position.x - x)*(position.x - x) + (position.y - y)*(position.y - y));
        //Log.d("kek", "Dist: " + f);
        return f;
    }

    protected void changeTexture(String newGraphics) {
        //Log.d("kek", "New graphics: " + newGraphics);
        graphics= imgRes.getTexture(newGraphics);
    }

    public void rescale(int oldTileSize) {
        int tileSizeHalf= oldTileSize / 2;

        Point off= new Point (position.x % oldTileSize, position.y % oldTileSize);
        Point newOff= new Point(
                (int)(screen.TILE_SIZE * (off.x / (float)oldTileSize)),
                (int)(screen.TILE_SIZE * (off.y / (float)oldTileSize))
        );

        Point mapPos= new Point(
                position.x / oldTileSize + (off.x > tileSizeHalf ? 1 : 0),
                position.y / oldTileSize + (off.y > tileSizeHalf ? 1 : 0)
        );
        Point newPos= screen.calcPosition(mapPos.x, mapPos.y);
        setPosition(new Point(
            newPos.x + newOff.x,
            newPos.y + newOff.y
        ));
    }

    public static void setProgressRef(PlayerProgress prg) {
        progress= prg;
    }

    public static void setStateRef(LevelState st) {
        state= st;
    }
}
