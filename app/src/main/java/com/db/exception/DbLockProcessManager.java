package com.db.exception;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.db.exception.db.SQLite_db;

//android.database.sqlite.SQLiteDatabaseLockedException: database is locked
public class DbLockProcessManager extends ErrorManager {

    private SQLite_db db;

    @Override
    public void error(Context context) {
        db = SQLite_db.getInstance(context.getApplicationContext());
        doDbWork();
        Intent intent = new Intent();
        intent.setClass(context, ProcessService.class);
        context.startService(intent);
    }



    private void doDbWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    db.insertNewTask(2, "service");
                }
            }
        }).start();
    }
}
