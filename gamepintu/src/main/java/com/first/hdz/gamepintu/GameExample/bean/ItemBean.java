package com.first.hdz.gamepintu.GameExample.bean;

import android.graphics.Bitmap;

/**
 * created by hdz
 * on 2018/9/7
 */
public class ItemBean {


    private int mItemId;
    private int mBitmapId;
    private Bitmap mBitmap;

    public ItemBean() {

    }

    public ItemBean(int mItemId, int mBitmapId, Bitmap mBitmap) {
        this.mItemId = mItemId;
        this.mBitmapId = mBitmapId;
        this.mBitmap = mBitmap;
    }

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public int getBitmapId() {
        return mBitmapId;
    }

    public void setBitmapId(int mBitmapId) {
        this.mBitmapId = mBitmapId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

}
