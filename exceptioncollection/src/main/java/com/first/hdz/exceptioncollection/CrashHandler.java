package com.first.hdz.exceptioncollection;

import android.content.Context;

/**
 * created by hdz
 * on 2018/8/9
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";


    public void init(Context context) {

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此，
     * 可以报程序错误都在此处发送给服务端并进行收集，以便及时修
     * 改bug，更新应用，减少用户的不好体验
     *
     * @param ex
     * @return
     */
    private boolean handleException(Throwable ex) {

        return false;
    }

    /**
     * 收集设备信息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {

    }

    private String saveCrashInfo2File(Throwable ex) {

        return "";
    }

}
