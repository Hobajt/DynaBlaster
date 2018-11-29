package com.kopecrad.dynablaster.game.objects.graphics;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.graphics.spritesheet.SpritesheetData;
import com.kopecrad.dynablaster.game.objects.graphics.spritesheet.SpritesheetDataPool;

import java.util.ArrayList;
import java.util.List;

public class Animation implements ObjectGraphics {

    private static final float ANIM_SPEED_BASE = 10;

    private static SpritesheetDataPool sData;

    private float innerCounter;

    private float speedDelay;

    private Bitmap[] frames;
    private int frame;

    public void update() {
        innerCounter += LevelState.getDeltaTime();
        if(innerCounter >= speedDelay) {
            innerCounter = 0;
            if (++frame >= frames.length - 1)
                frame = 0;
        }
    }

    public Animation(String identifier, Bitmap sheet, int sheetID) {
        frame= 0;
        innerCounter= 0;
        if(sheetID != ImageResources.TEXTURE_DEFAULT) {
            this.frames= processSpritesheet(sheet, identifier);
        }
        else {
            this.frames= new Bitmap[] {sheet};
        }
    }

    @Override
    public Bitmap getFrame() {
        return frames[frame];
    }

    private Bitmap[] processSpritesheet(Bitmap sheet, String identifier) {
        if(sData == null)
            sData= new SpritesheetDataPool();

        SpritesheetData data= sData.getData(identifier);

        Point size= data.getImageSize(sheet);
        int count= data.getCount();
        speedDelay= data.getSpeed() / ANIM_SPEED_BASE;

        List<Bitmap> bmp= new ArrayList<>();
        for(int i= 0; i < data.getRowCount(); i++) {
            for(int j= 0; j < data.getColCount(); j++) {
                if(i * data.getColCount() + j >= count)
                    return bmp.toArray(new Bitmap[bmp.size()]);
                bmp.add(Bitmap.createBitmap(sheet, size.x*j, size.y*i, size.x, size.y));
            }
        }

        return bmp.toArray(new Bitmap[bmp.size()]);
    }
}
