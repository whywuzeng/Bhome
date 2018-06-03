package com.system.bhouse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017-03-15.
 * ClassName: com.system.bhouse.db
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "BH_db";
    private static final int DB_VERSION = 1;
    private static DBHelper mDbHelper;

    public static final String LNKTOOLS_TABLE="lnktools";//快捷方式

    public static final String SEARCH_RECORD_TABLE = "search_record";//搜索记录

    private static final String CREATE_SEARCH_RECORD_TABLE="create table if not exists  "+SEARCH_RECORD_TABLE+"(name text)";


    public static synchronized DBHelper getInstance(Context context)
    {
        if (mDbHelper==null)
        {
            mDbHelper=new DBHelper(context);
        }
        return mDbHelper;
    }


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SEARCH_RECORD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+SEARCH_RECORD_TABLE);
        onCreate(db);
    }
}
