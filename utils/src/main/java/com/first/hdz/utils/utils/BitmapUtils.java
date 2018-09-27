package com.first.hdz.utils.utils;

import android.graphics.Bitmap;

/**
 * created by hdz
 * on 2018/9/10
 */
public class BitmapUtils {

    public static Bitmap drawCircle(int resId) {

        return null;
    }

    public static Bitmap drawCircle(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int cx = width / 2;
        int cy = height / 2;
        //半径
        int r = width < height ? width / 2 - 1 : height / 2 - 1;
        int startY = cy - r;
        int startX = cx - r;
        int endX = cx + r;
        int endY = cy + r;
        Bitmap result = Bitmap.createBitmap(2 * r, 2 * r, Bitmap.Config.ARGB_8888);
        int pixels[] = new int[width * height];
        int newPixel[] = new int[4 * r * r];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = startY; i < endY; i++) {
            for (int j = startX; j < endX; j++) {
                newPixel[(i - startY) * 2 * r + (j - startX)] = IsInR(i, j, cx, cy, r) ? pixels[i * width + j] : 0x00000000;
            }
        }
        bitmap.recycle();
        bitmap = null;
        result.setPixels(newPixel, 0, 2 * r, 0, 0, 2 * r, 2 * r);
        return result;
    }

    public static Bitmap drawCircle() {
        return null;
    }

    /**
     * 判断点是否属于圆形内
     *
     * @param x
     * @param y
     * @param cx
     * @param cy
     * @param r
     * @return
     */
    private static boolean IsInR(int x, int y, int cx, int cy, int r) {
        return Math.pow(Math.abs(x - cx), 2) + Math.pow(Math.abs(y - cy), 2) <= Math.pow(r - 0.5f, 2);

    }
}
