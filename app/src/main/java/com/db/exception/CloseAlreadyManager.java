package com.db.exception;

import android.content.Context;

import com.db.exception.db.SQLiteAlreadyCloseDb;


//java.lang.IllegalStateException: Cannot perform this operation because the connection pool has been closed.
public class CloseAlreadyManager extends ErrorManager{
    private String TAG = "DEADLOCK";
    private SQLiteAlreadyCloseDb mSqliteDb = null;

    Runnable run1 = new Runnable() {
        @Override
        public void run() {

            try {
                while(true) {
                    mSqliteDb.query();//这里只要多次就能必现，为了简单复现增加了while循环
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
                while(true) {
                    mSqliteDb.query();//这里只要多次就能必现，为了简单复现增加了while循环
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void error(Context context) {
        mSqliteDb = SQLiteAlreadyCloseDb.getInstance(context.getApplicationContext());
        new Thread(run1,"Thread-1").start();
        new Thread(run2,"Thread-2").start();
    }
}
