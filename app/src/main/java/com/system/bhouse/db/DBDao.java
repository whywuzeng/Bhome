package com.system.bhouse.db;

import android.database.sqlite.SQLiteDatabase;

import com.system.bhouse.base.App;

/**
 * Created by Administrator on 2017-03-15.
 * ClassName: com.system.bhouse.db
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DBDao {

    protected final SQLiteDatabase mSQLiteDatabase;

    public DBDao()
    {
        mSQLiteDatabase= App.getDB();
    }
}
