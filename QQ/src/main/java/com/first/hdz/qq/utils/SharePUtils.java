package com.first.hdz.qq.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * created by hdz
 * on 2018/10/29
 */
public class SharePUtils {

    private static final String TAG = "QQ_SharePUtils";

    public static final String APP = "QQ";

    public static final String IP = "ip";

    public static final String IS_LOGIN = "isLogin";


    private static SharedPreferences createInstance(Context context) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }
        Context con = context.getApplicationContext();
        return con.getSharedPreferences(APP, Context.MODE_PRIVATE);
    }

    public static void putValue(Context context, String keys, Object values) {
        SharedPreferences share = createInstance(context);
        if (share == null) {
            LogUtils.e(TAG, "SharedPreference is null");
            return;
        }
        SharedPreferences.Editor editor = share.edit();
        if (values instanceof String) {
            editor.putString(keys, (String) values);
        } else if (values instanceof Integer) {
            editor.putInt(keys, (Integer) values);
        } else if (values instanceof Long) {
            editor.putLong(keys, (Long) values);
        } else if (values instanceof Float) {
            editor.putFloat(keys, (Float) values);
        } else if (values instanceof Boolean) {
            editor.putBoolean(keys, (Boolean) values);
        }
        editor.apply();
    }

    public static Object getValue(Context context, String keys, Object defaultValue) {
        SharedPreferences share = createInstance(context);
        if (share == null) {
            return null;
        }
        if (defaultValue instanceof String) {
            return share.getString(keys, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return share.getInt(keys, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return share.getLong(keys, (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            return share.getFloat(keys, (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return share.getBoolean(keys, (Boolean) defaultValue);
        }
        return null;
    }
}
