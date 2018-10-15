package com.first.hdz.qq.utils;

/**
 * created by hdz
 * on 2018/9/29
 */
public interface ProgressListener {

    // all the method in mainThread
    public void onStart();

    public void onUpdateProgress(long progress, long length);

    public void onEnd(String filePath);

}
