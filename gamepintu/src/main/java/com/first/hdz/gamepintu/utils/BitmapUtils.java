package com.first.hdz.gamepintu.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hdz
 * on 2018/9/6
 */
public class BitmapUtils {

    public static final int LEVEL_SIMPLE = 2;
    public static final int LEVEL_NORMAL = 3;
    public static final int LEVEL_DIFFICULT = 4;

    /**
     * @param level
     * @param bitmap
     * @param context
     * @return
     */
    public static final int get(int level, Bitmap bitmap, Context context) {
        Bitmap bit = null;
        List<Bitmap> bitmapList = new ArrayList<>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieces = (int) Math.pow(level, 2);
        int itemWidth = width / pieces;
        int itemHeight = height / pieces;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < level; i++) {
            for (int j = 1; j < level; j++) {
                bit = Bitmap.createBitmap(
                        bitmap,                    // 切割的source
                        j * itemWidth,          //  剪切x方向的起始位置
                        i * itemHeight,         //  剪切y方向的起始位置
                        itemWidth,                 //  剪切的宽度
                        itemHeight);               //  剪切的长度
                bitmapList.add(bit);

            }
        }
        return 0;
    }
}
