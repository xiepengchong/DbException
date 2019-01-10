package com.db.exception.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


//android.database.sqlite.SQLiteDatabaseLockedException: database is locked
public class SQLiteSingleDb {


    private static SQLiteSingleDb sqlite;
    private SqliteHelper sqliteHelper;

    private Context mContext;
    private static final String TAG = "SQLite_db";

    private SQLiteSingleDb(Context context) {
        this.sqliteHelper = new SqliteHelper(context);
        mContext = context;
    }

    public static synchronized SQLiteSingleDb getInstance(Context context) {
            sqlite = new SQLiteSingleDb(context);
        return sqlite;
    }

    private synchronized void  exeDO(String sql) {
        SQLiteDatabase sqLiteDatabase = sqliteHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
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
}
