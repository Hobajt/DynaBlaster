package com.kopecrad.dynablaster.game.objects.graphics;

import android.graphics.Bitmap;

public class Texture implements ObjectGraphics {
    private Bitmap texture;

    public Texture(Bitmap texture) {
        this.texture = texture;
    }

    @Override
    public Bitmap getFrame() {
        return texture;
    }
}
