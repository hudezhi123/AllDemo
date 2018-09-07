package com.first.hdz.utils.utils;

/**
 * created by hdz
 * on 2018/9/7
 */
public class NotInitException extends Exception {
    public NotInitException(String TAG) {
        super(TAG + "----->" + "is not init");
    }

}
