package com.db.exception;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.db.exception.db.SQLite_db;

public class ProcessService extends Service {
    public ProcessService() {
    }
    private SQLite_db db;
    private String TAG = "Service";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = SQLite_db.getInstance(getApplicationContext());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        doDbWork();

        return START_STICKY;
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
