package com.first.hdz.qq.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * created by hdz
 * on 2018/9/17
 */
public class DisplayUtils {

    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }


}
