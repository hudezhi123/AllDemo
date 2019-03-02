package com.first.hdz.qq.utils;

import android.util.Log;

/**
 * created by hdz
 * on 2018/9/7
 */
public class LogUtils {

    private static final String TAG = "LogUtils";

    private LogUtils() {
    }

    public static boolean DEBUG = false;

    /**
     * 输出开发者必须看到的内容：导致程序出错的内容
     *
     * @param TAG
     * @param message
     */
    public static void e(String TAG, String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }

    /**
     * 输出开发者想要看到的内容
     *
     * @param TAG
     * @param message
     */
    public static void i(String TAG, String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    /**
     * 调试内容
     *
     * @param TAG
     * @param message
     */
    public static void d(String TAG, String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }


    public static void v(String TAG, String message) {
        if (DEBUG) {
            Log.v(TAG, message);
        }
    }


    private static void writeLog(String msg) {

    }
}
