package com.first.hdz.qq.utils;

import android.text.TextUtils;

/**
 * created by hdz
 * on 2018/9/26
 */
public class StringUtils {

    public static boolean isEmpty(String content) {
        return TextUtils.isEmpty(content) || "null".equals(content);
    }


}
