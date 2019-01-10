package com.db.exception.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLite_db {


    private static SQLite_db sqlite;
    private SqliteHelper sqliteHelper;

    private Context mContext;
    private static final String TAG = "SQLite_db";

    private SQLite_db(Context context) {
        this.sqliteHelper = new SqliteHelper(context);
        mContext = context;
    }

    public synchronized SQLiteDatabase getWriteableDb(){
        return sqliteHelper.getWritableDatabase();
    }

    public synchronized SQLiteDatabase getReadableDb(){
        return sqliteHelper.getReadableDatabase();
    }

    public static synchronized SQLite_db getInstance(Context context) {
        if (sqlite == null )
            sqlite = new SQLite_db(context);
        return sqlite;
    }

    private synchronized void  exeDO(String sql) {
        SQLiteDatabase sqLiteDatabase = sqliteHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    public void insertNewTask(int tid, String endstr) {
        //String sql = "insert into table_task (tid,endstr) values(tid,endstr)";
        for(int i=0;i<1000;i++) {
            StringBuilder sql = new StringBuilder(200);
            sql.append("insert into ");
            sql.append(SqliteHelper.TABLE_NAME_TASK);
            sql.append(" (tid,endstr) values(");
            sql.append(tid+i);
            sql.append(",'");
            sql.append(endstr);
            sql.append("')");
            exeDO(sql.toString());
        }
    }

    public void insertOneTask(int tid, String endstr) {
        //String sql = "insert into table_task (tid,endstr) values(tid,endstr)";
        StringBuilder sql = new StringBuilder(200);
        sql.append("insert into ");
        sql.append(SqliteHelper.TABLE_NAME_TASK);
        sql.append(" (tid,endstr) values(");
        sql.append(tid);
        sql.append(",'");
        sql.append(endstr);
        sql.append("')");
        exeDO(sql.toString());
    }

    public void dropTable(){
        StringBuilder sql = new StringBuilder(200);
        sql.append("DROP TABLE table_task;");
        exeDO(sql.toString());
    }

    private synchronized Cursor  exeQuery(String sql) {
        SQLiteDatabase sqLiteDatabase = sqliteHelper.getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }
    public void query() {
        StringBuilder sql = new StringBuilder(200);
        sql.append("select * from table_task;");
        Cursor cursor =   exeQuery(sql.toString());
        if (cursor.moveToFirst()) {
            do {
                String tid = cursor.getString(cursor.getColumnIndex("tid"));
                String endstr = cursor.getString(cursor.getColumnIndex("endstr"));
            } while (cursor.moveToNext());
        }
    }}
