package com.db.exception;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.db.exception.db.SQLite_db;
import com.db.exception.db.SqliteHelper;

public class TableLockManager extends ErrorManager{
    private String TAG = "TABLELOCK";
    static Object sLocker = new Object();
    private SQLite_db mSqliteDb = null;

    Runnable run1 = new Runnable() {
        @Override
        public void run() {

            try {
                Log.d(TAG, "run1 Before synchronized");
                Thread.sleep(5);
                mSqliteDb.query();
                Log.d(TAG, "run1 after synchronized");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    Runnable run2 = new Runnable() {
        @Override
        public void run() {

            try {

                Log.d(TAG, "run2 Before synchronized");
                mSqliteDb.dropTable();
                Log.d(TAG, "run2 After beginTransaction");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void error(Context context) {
        mSqliteDb = SQLite_db.getInstance(context.getApplicationContext());
        new Thread(run1,"Thread-1").start();
        new Thread(run2,"Thread-2").start();
    }
}
