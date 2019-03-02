package com.first.hdz.qq.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * created by hdz
 * on 2018/9/29
 * 下载文件并且回调进度数据，但是不显示进度条，
 * 进度条可以控制形式：窗口（Dialog），ProgressBar或者自定义的进度条
 */
public class ProgressDownload {

    private ProgressListener mListener;

    private String apkUrl;

    private Context context;

    private volatile boolean IsRun = false;

    public ProgressDownload(Context context, String apkUrl, ProgressListener progressListener) {
        this.context = context;
        this.apkUrl = apkUrl;
        this.mListener = progressListener;
    }

    public void start() {
        IsRun = true;
        new FileAsyncTask(false).execute(apkUrl);
    }

    public void pause() {
        IsRun = false;
    }

    public void restart() {
        IsRun = true;
        new FileAsyncTask(true).execute(apkUrl);
    }

    public void cancel() {

    }

    private class FileAsyncTask extends AsyncTask<String, Integer, String> {

        private DBManager dbManager;
        private boolean IsRestart;


        private FileAsyncTask(boolean IsRestart) {
            this.IsRestart = IsRestart;
            dbManager = DBManager.getInstance(context.getApplicationContext());
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mListener != null) {
                mListener.onStart();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mListener != null) {
                mListener.onUpdateProgress(values[0], values[1]);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            if (IsRestart) {
                downloadApk(url, true);
            } else {
                downloadApk(url);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mListener != null) {
                mListener.onEnd(s);
            }
        }

        private void downloadApk(String apkUrl) {
            try {
                int pauseLen = 0;
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    // TODO: 2018/9/28
                    long length = conn.getContentLength();
                    InputStream input = conn.getInputStream();
                    String fileDir = FileUtils.mkDir(context, FileUtils.DIR_TYPE_APK, FileUtils.LOCAL_EXTERNAL);
                    File file = new File(fileDir, Constants.APK_NAME);
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    RandomAccessFile access = new RandomAccessFile(file, "rw");
                    access.setLength(length);
                    int len = 0;
                    byte[] buffer = new byte[1024 * 4];
                    while ((len = input.read(buffer)) != -1 && IsRun) {
                        access.write(buffer, 0, len);
                        pauseLen += len;
                        mListener.onUpdateProgress(pauseLen, length);
                    }
                    conn.disconnect();
                    // TODO: 2018/9/29   插入数据库
                    insert(pauseLen, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void downloadApk(String apkUrl, boolean isRestart) {
            try {
                int[] start_end = query();
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Range", "byte:" + start_end[0] + "-" + start_end[1]);
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    // TODO: 2018/9/28
                    InputStream input = conn.getInputStream();
                    RandomAccessFile accessFile = new RandomAccessFile("fileName", "rw");
                    accessFile.seek(start_end[0]);
                    int len = 0;
                    byte[] buffer = new byte[1024 * 4];
                    while ((len = input.read(buffer)) != -1 && IsRun) {
                        accessFile.write(buffer, 0, len);
                        start_end[0] += len;
                        mListener.onUpdateProgress(start_end[0], start_end[1]);
                    }
                    // TODO: 2018/9/29   更新
                    update(start_end[0], start_end[1]);
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void insert(long start, long length) {
            dbManager.openDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.DOWNLOAD_FILE_LENGTH, length);
            values.put(DBHelper.DOWNLOAD_FILE_TYPE, "apk");
            values.put(DBHelper.DOWNLOAD_URL, apkUrl);
            values.put(DBHelper.DOWNLOAD_NAME, Constants.APK_NAME);
            values.put(DBHelper.DOWNLOAD_PAUSE_POINT, start);
            values.put(DBHelper.DOWNLOAD_STATUS, false);
            dbManager.insert(DBHelper.TB_FILE_DOWNLOAD, values);
            dbManager.closeDatabase();
        }

        private void update(int pausePoint, int length) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.DOWNLOAD_PAUSE_POINT, pausePoint);
            values.put(DBHelper.DOWNLOAD_FILE_LENGTH, length);
            if (pausePoint == length) {
                values.put(DBHelper.DOWNLOAD_STATUS, true);
            } else {
                values.put(DBHelper.DOWNLOAD_STATUS, false);
            }
            dbManager.update(DBHelper.TB_FILE_DOWNLOAD, values, DBHelper.DOWNLOAD_URL + "=？", new String[]{apkUrl});
        }

        /**
         * @return 返回 start 和 end
         */
        private int[] query() {
            dbManager.openDatabase();
            Cursor cursor = dbManager.query(
                    DBHelper.TB_FILE_DOWNLOAD,
                    new String[]{DBHelper.DOWNLOAD_PAUSE_POINT, DBHelper.DOWNLOAD_FILE_LENGTH},
                    DBHelper.DOWNLOAD_URL + "=?",
                    new String[]{apkUrl});
            cursor.moveToFirst();
            int end = cursor.getInt(cursor.getColumnIndex(DBHelper.DOWNLOAD_FILE_LENGTH));
            int start = cursor.getInt(cursor.getColumnIndex(DBHelper.DOWNLOAD_PAUSE_POINT));
            cursor.close();
            dbManager.closeDatabase();
            return new int[]{start, end};
        }

        private void delete() {

        }

    }
}
