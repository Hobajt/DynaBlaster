package com.kopecrad.dynablaster.game.objects;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;

public abstract class ObjectFactory {

    private static ImageResources res;

    public static void setResourceRef(ImageResources r) {
        res= r;
    }

    protected static ImageResources getRes() {
        return res;
    }
}
