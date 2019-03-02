package com.first.hdz.qq.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by hdz
 * on 2018/9/6
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashHandler.class.getSimpleName();

    private static final int SHOW_TOAST = 1000;

    private static final String DIR = "/GRT/log/";

    private static CrashHandler mInstance = new CrashHandler();

    private static final boolean DEBUG = true;

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;

    private static String mFileName = "";

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    String tips = (String) msg.obj;
                    Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private CrashHandler() {

    }

    public static CrashHandler getInstance(String fileName) {
        mFileName = fileName;
        return mInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        if (throwable != null) {
            if (!handleException(throwable) && mDefaultCrashHandler != null) {
                mDefaultCrashHandler.uncaughtException(thread, throwable);
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        }

    }


    /**
     * 如果处理了错误就返回 true
     *
     * @param ex
     * @return
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        } else {
            String message = ex.getMessage();
            Message msg = mHandler.obtainMessage();
            msg.what = SHOW_TOAST;
            msg.obj = message;
            mHandler.sendMessage(msg);
            try {
                dumpExceptionToSDCard(ex);
                uploadToServer(ex);
                return true;
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
        }
    }

    /**
     * 将错误信息上传到服务端
     *
     * @param ex
     */
    private void uploadToServer(Throwable ex) {
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        writer.close();
        String result = writer.toString();
        sb.append(result);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File fileDir = new File(rootDir + DIR);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(fileDir, mFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file, true);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            output.write(("---->" + timeStamp).getBytes());
            output.write(sb.toString().getBytes());
            output.write("-------------------------------------------------------分割线----".getBytes());
            output.close();
        }
    }


}
