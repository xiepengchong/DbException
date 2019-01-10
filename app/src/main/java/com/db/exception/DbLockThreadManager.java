package com.db.exception;

import android.content.Context;
import android.util.Log;

import com.db.exception.db.SQLiteSingleDb;

//android.database.sqlite.SQLiteDatabaseLockedException: database is locked
public class DbLockThreadManager extends ErrorManager {
    private SQLiteSingleDb mSqliteDb = null;
    private Context context;
    private String TAG = "DBLOCK";
    @Override
    public void error(Context context) {
        new Thread(run1,"Thread-1").start();
        new Thread(run2,"Thread-2").start();
        this.context = context;
    }

    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            try {
                while(true) {//加入while循环是为了必现
                    mSqliteDb = SQLiteSingleDb.getInstance(context);
                    mSqliteDb.insertOneTask(100, "sss");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            try {
                while(true) {//加入while循环是为了必现
                    mSqliteDb = SQLiteSingleDb.getInstance(context);
                    mSqliteDb.insertOneTask(100, "sss");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
