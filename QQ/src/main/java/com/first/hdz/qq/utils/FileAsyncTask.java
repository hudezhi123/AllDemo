package com.first.hdz.qq.utils;

/**
 * created by hdz
 * on 2018/9/29
 */


import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * String Url路径
 * Integer 进度
 * String 文件路径
 */
public class FileAsyncTask extends AsyncTask<String, Long, String> {


    private ProgressListener mProgressListener;

    private Context context;

    public FileAsyncTask(Context context, ProgressListener progressListener) {
        this.context = context;
        this.mProgressListener = progressListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressListener != null) {
            mProgressListener.onStart();
        }
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        if (mProgressListener != null) {
            mProgressListener.onUpdateProgress(values[0], values[1]);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        return downloadFile(url);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mProgressListener != null) {
            mProgressListener.onEnd(s);
        }
    }

    private String downloadFile(String apkUrl) {
        try {
            String fileName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
            long pauseLen = 0;
            URL url = new URL(apkUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                // TODO: 2018/9/28
                long length = conn.getContentLength();
                publishProgress(0L, length);
                InputStream input = conn.getInputStream();
                String fileDir = FileUtils.mkDir(context, FileUtils.DIR_TYPE_APK, FileUtils.LOCAL_EXTERNAL);
                File file = new File(fileDir, fileName);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                RandomAccessFile access = new RandomAccessFile(file, "rw");
                access.setLength(length);
                int len = 0;
                byte[] buffer = new byte[1024 * 4];
                while ((len = input.read(buffer)) != -1) {
                    access.write(buffer, 0, len);
                    pauseLen += len;
                    publishProgress(pauseLen, length);
                }
                conn.disconnect();
                return file.getAbsolutePath();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }


}