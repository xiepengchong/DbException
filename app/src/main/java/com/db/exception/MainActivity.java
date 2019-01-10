package com.db.exception;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.db.exception.db.SQLite_db;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDeadLock;
    private Button mDbLockedThread;
    private Button mDbLockedProcess;
    private Button mTableLock;
    private Button mAlreadyClose;
    private ErrorManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeadLock = findViewById(R.id.locked_dead);
        mDbLockedThread = findViewById(R.id.db_locked_thread);
        mDbLockedProcess = findViewById(R.id.db_locked_process);
        mTableLock = findViewById(R.id.table_lock);
        mAlreadyClose = findViewById(R.id.already_close);

        mDeadLock.setOnClickListener(this);
        mDbLockedThread.setOnClickListener(this);
        mDbLockedProcess.setOnClickListener(this);
        mTableLock.setOnClickListener(this);
        mAlreadyClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mDeadLock){
            manager = new DeadLockManager();
        } else if(v == mDbLockedProcess){
            manager = new DbLockProcessManager();
        } else if(v == mDbLockedThread){
            manager = new DbLockThreadManager();
        } else if(v == mTableLock){//未复现预期错误，只是表无法找到
            SQLite_db.getInstance(this).insertNewTask(10,"sss");
            manager = new TableLockManager();
        } else if(v == mAlreadyClose){
            manager = new CloseAlreadyManager();
        }
        manager.error(this);
    }



}
