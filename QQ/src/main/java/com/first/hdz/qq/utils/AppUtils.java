package com.first.hdz.qq.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.first.hdz.qq.R;

import java.util.HashMap;

/**
 * created by hdz
 * on 2018/9/27
 */
public class AppUtils {
    public static String getVersionName(Context context) {
        PackageInfo info = null;
        try {
            info = getPackageInfo(context);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }


    public static long getVersionCode(Context context) {
        PackageInfo info = null;
        try {
            info = getPackageInfo(context);
            return info.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static HashMap<String, Object> getLoginParam(Context context) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("appName", context.getString(R.string.app_name));
        params.put("appType", 1);  //1 android ;0 ios
        params.put("appId", "QQ");
        return params;
    }


    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        return manager.getPackageInfo(context.getPackageName(), 0);
    }
}
