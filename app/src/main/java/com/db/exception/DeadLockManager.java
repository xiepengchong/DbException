package com.db.exception;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.db.exception.db.SQLite_db;
import com.db.exception.db.SqliteHelper;
//com.db.exception W/SQLiteConnectionPool: The connection pool for database '/data/user/0/com.db.exception/databases/my_db' has been unable to grant a connection to thread 15497 (Thread-2) with flags 0x2 for 30.005001 seconds.
//    Connections: 0 active, 1 idle, 0 available.
public class DeadLockManager extends ErrorManager{
    private String TAG = "DEADLOCK";
    static Object sLocker = new Object();
    private SQLite_db mSqliteDb = null;

    Runnable run1 = new Runnable() {
        @Override
        public void run() {

            try {
                SQLiteDatabase db = mSqliteDb.getWriteableDb();
                Log.d(TAG, "run1 Before beginTransaction");
                db.beginTransaction();
                Thread.sleep(3000);

                Log.d(TAG, "run1 After beginTransaction");
                synchronized (sLocker)
                {
                    Log.d(TAG, "run1 In synchronized");
                }

                try{
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
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

                Log.d(TAG, "run2 Before synchronized");

                Thread.sleep(3000);

                SQLiteDatabase db = mSqliteDb.getWriteableDb();

                Log.d(TAG, "run2 Before beginTransaction");
                synchronized (sLocker) {
                    db.replace(SqliteHelper.TABLE_NAME_TASK, null, new ContentValues());
                }
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
