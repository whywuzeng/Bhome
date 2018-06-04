package com.system.bhouse.bhouse.setup.notification.receiver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {

        super(context, "Bhome.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notification (id integer primary key autoincrement,msg_id varchar(64),title varchar(128),activity varchar(256),notificationActionType varchar(512),content text,update_time varchar(16),isnew boolean)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
