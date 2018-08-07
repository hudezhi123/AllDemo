package com.first.hdz.customview.rain;

import android.location.Location;

/**
 * created by hdz
 * on 2018/8/7
 */
public class Drop {

    public static final int MAX_R = 100;
    public static final int MIN_R = 50;
    public static final int ALPHA = 255;

    private int pointX;
    private int pointY;
    private int color;
    private int alpha;
    private int radius;

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}
