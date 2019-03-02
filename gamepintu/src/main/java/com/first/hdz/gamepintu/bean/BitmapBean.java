package com.first.hdz.gamepintu.bean;

import android.graphics.Bitmap;

/**
 * created by hdz
 * on 2018/9/6
 */
public class BitmapBean {

    private int id;
    private int order;
    private Bitmap bitmap;

    public BitmapBean() {
    }

    public BitmapBean(int id, int order, Bitmap bitmap) {
        this.id = id;
        this.order = order;
        this.bitmap = bitmap;
    }

}
