package com.first.hdz.qq.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by hdz
 * on 2018/9/28
 */
public class DBManager {
    private AtomicInteger mCount = new AtomicInteger(0);
    public static DBManager mInstance;
    public static SQLiteOpenHelper mHelper;
    private static SQLiteDatabase mDatabase;

    private DBManager() {
    }

    private DBManager(Context context) {
        mHelper = new DBHelper(context);
    }

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManager(context);
        }
        return mInstance;
    }

    /**
     * 打开数据库
     *
     * @return
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mCount.incrementAndGet() == 1) {
            mDatabase = mHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * 关闭数据库
     */
    public synchronized void closeDatabase() {
        if (mCount.decrementAndGet() == 0) {
            mDatabase.close();
        }
        mDatabase.close();
    }

    /**
     * 返回新添加记录的行号，该行号是一个内部值，与主键id 无关，发生错误返回－1
     * 不管values是否为NULL，执行insert()，总会添加一条记录，如果values=null, 则会添加一条除主键之外，其他字段都为NULL的记录。
     *
     * @param tableName
     * @param values
     * @return
     */
    public long insert(String tableName, ContentValues values) {
        return mDatabase.insert(tableName, null, values);
    }

    /**
     * @param table       数据表名
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return mDatabase.update(table, values, whereClause, whereArgs);
    }

    public int delete(String table, String whereClause, String whereArgs) {
        return mDatabase.delete(table, whereClause, new String[]{whereArgs});
    }

    /**
     * @param distinct      指定是否去除重复记录
     * @param table         执行查询数据的表名
     * @param columns       要查询出来的列名。如select语句select关键字后面的部分
     * @param whereClause   查询条件子句，where关键字后面的部分，在条件子句中允许使用占位符”?”
     * @param selectionArgs 用于为whereClause子句中的占位符传入参数，值在数组中的位置与占位符在语句中的位置必须一致。否则就会有异常。
     * @param groupBy       相当于select语句groupBy关键字后面的部分
     * @param having        相当于select语句having关键字后面的部分
     * @param oderBy        相当于select语句orderby关键字后面的部分
     * @param limit         limit参数控制最多查询几条记录(用于控制分页的参数
     * @return 返回一个Cursor对象
     */
    private Cursor query(boolean distinct, String table, String[] columns,
                         String whereClause, String[] selectionArgs,
                         String groupBy, String having,
                         String oderBy, String limit) {
        return mDatabase.query(distinct, table, columns, whereClause, selectionArgs, groupBy, having, oderBy, limit);
    }

    public Cursor query(String table, String[] columns, String whereClause, String selectionArgs[]) {
        return query(true, table, columns, whereClause, selectionArgs, null, null, null, null);
    }

    public void executeSQL(String sql) {
        mDatabase.execSQL(sql);
    }

    public Cursor executeQuery(String sql, String[] selectionArgs) {
        return mDatabase.rawQuery(sql, selectionArgs);
    }

}
