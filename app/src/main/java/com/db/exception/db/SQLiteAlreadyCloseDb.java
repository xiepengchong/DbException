package com.db.exception.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


//java.lang.IllegalStateException: Cannot perform this operation because the connection pool has been closed.
public class SQLiteAlreadyCloseDb {


    private static SQLiteAlreadyCloseDb sqlite;
    private SqliteHelper sqliteHelper;

    private Context mContext;
    private static final String TAG = "SQLite_db";

    private SQLiteAlreadyCloseDb(Context context) {
        this.sqliteHelper = new SqliteHelper(context);
        mContext = context;
    }

    public static synchronized SQLiteAlreadyCloseDb getInstance(Context context) {
        if (sqlite == null )
            sqlite = new SQLiteAlreadyCloseDb(context);
        return sqlite;
    }


    public void query() {
        StringBuilder sql = new StringBuilder(200);
        sql.append("select * from table_task;");
        SQLiteDatabase sqLiteDatabase = sqliteHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql.toString(),null);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (cursor.moveToFirst()) {
            do {
                String tid = cursor.getString(cursor.getColumnIndex("tid"));
                String endstr = cursor.getString(cursor.getColumnIndex("endstr"));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
    }
}
