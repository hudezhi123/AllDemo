package com.first.hdz.gamepintu.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * created by hdz
 * on 2018/9/6
 */
public class DisplayUtils {

    private static final String TAG = "DisplayUtils";

    /**
     * there is two kinds of method to get values about metrics
     *
     * @ FLAG_ONE get from WindowManager
     * @ FLAG_TWO get from getResource
     */
    private static final int FLAG_ONE = 1;
    private static final int FLAG_TWO = 2;

    /**
     * @param context
     * @return
     */
    private static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (wm != null) {
            display = wm.getDefaultDisplay();
        } else {
            throw new NullPointerException("windowManager is null in DisplayUtils");
        }
        display.getMetrics(metrics);
        return metrics;
    }

    /**
     * @param context
     * @param flag
     * @return
     */
    public static DisplayMetrics getScreenSize(Context context, int flag) {
        DisplayMetrics metrics = null;
        switch (flag) {
            case FLAG_ONE:
                metrics = getScreenSize(context);
                break;
            case FLAG_TWO:
                metrics = context.getResources().getDisplayMetrics();
                break;
            default:
                metrics = context.getResources().getDisplayMetrics();
                break;
        }
        return metrics;
    }

    public static float getDeviceDensity(Context context) {
        return getScreenSize(context, FLAG_TWO).density;
    }

    public static float getDeviceDensityDpi(Context context) {
        return getScreenSize(context, FLAG_TWO).densityDpi;
    }

    /**
     * 将dp转化为px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }


    /**
     * 将px转化为dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 将sp转化为px
     *
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5f);
    }

    /**
     * 将px转化为sp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2sp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / density + 0.5f);
    }
}
