package com.first.hdz.qq.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * created by hdz
 * on 2018/9/28
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "qq.db";
    private static final int VERSION = 1;

    public static final String TB_FILE_DOWNLOAD = "tb_file_download";
    public static final String DOWNLOAD_ID = "id";
    public static final String DOWNLOAD_NAME = "name";
    public static final String DOWNLOAD_URL = "url";
    public static final String DOWNLOAD_PAUSE_POINT = "pausePoint";
    public static final String DOWNLOAD_STATUS = "status";
    public static final String DOWNLOAD_FILE_TYPE = "type";
    public static final String DOWNLOAD_FILE_LENGTH = "length";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String download_sql = "create table if not exists " + TB_FILE_DOWNLOAD
                + "("
                + DOWNLOAD_ID + " integer primary key autoincrement not null,"
                + DOWNLOAD_NAME + " text,"
                + DOWNLOAD_URL + " text,"
                + DOWNLOAD_PAUSE_POINT + " text,"
                + DOWNLOAD_STATUS + " text,"
                + DOWNLOAD_FILE_TYPE + " text,"
                + DOWNLOAD_FILE_LENGTH + " text);";
        sqLiteDatabase.execSQL(download_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
