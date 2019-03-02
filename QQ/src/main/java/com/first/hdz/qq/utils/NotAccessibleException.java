package com.first.hdz.qq.utils;

/**
 * created by hdz
 * on 2018/9/7
 */
public class NotAccessibleException extends Exception {

    public NotAccessibleException(String message) {
        super(message);
    }

    public NotAccessibleException(String TAG, String message) {
        super(TAG + "----->" + message);
    }
}
