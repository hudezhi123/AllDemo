package com.first.hdz.utils.utils;

/**
 * created by hdz
 * on 2018/9/7
 */
public class DuplicateException extends Exception {

    public DuplicateException(String TAG) {
        super(TAG + "----->" + "is duplicated init");
    }
}
