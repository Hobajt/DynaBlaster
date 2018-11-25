package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Canvas;

/**
 * Callback implementation to for rendering.
 */
public interface Renderable {

    void renderUpdate(Canvas canvas);
}
