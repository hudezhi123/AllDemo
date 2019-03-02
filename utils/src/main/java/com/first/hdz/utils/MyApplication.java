package com.first.hdz.utils;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.first.hdz.utils.utils.CrashHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by hdz
 * on 2018/9/6
 */
public class MyApplication extends Application {

    public CrashHandler mCrashHandler;







    @Override
    public void onCreate() {
        super.onCreate();

    }


    private void init() {
        mCrashHandler = CrashHandler.getInstance("");
    }




}
