package com.system.bhouse.base.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/**
 * @author: wuchao
 * @date: 2017/11/26 22:22
 * @desciption:
 */

public class ReleaseOpenHelper extends DaoMaster.OpenHelper {

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ReleaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

//        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
//        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
//
//            @Override
//            public void onCreateAllTables(Database db, boolean ifNotExists) {
//                DaoMaster.createAllTables(db, ifNotExists);
//            }
//
//            @Override
//            public void onDropAllTables(Database db, boolean ifExists) {
//                DaoMaster.dropAllTables(db, ifExists);
//            }
//        },  MovieCollectDao.class);
    }
}
