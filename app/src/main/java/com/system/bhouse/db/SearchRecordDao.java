package com.system.bhouse.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.system.bhouse.bean.SearchHistroyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-15.
 * ClassName: com.system.bhouse.db
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class SearchRecordDao extends DBDao {
    public long add(SearchHistroyBean bean)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",bean.getSh_name());
        return mSQLiteDatabase.insert(DBHelper.SEARCH_RECORD_TABLE,null,contentValues);
    }

    public void addAll(List<SearchHistroyBean> listDatas)
    {
        for (SearchHistroyBean bean:listDatas)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",bean.getSh_name());
            mSQLiteDatabase.insert(DBHelper.SEARCH_RECORD_TABLE,null,contentValues);
        }
    }

    public void update(SearchHistroyBean bean)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",bean.getSh_name());
        mSQLiteDatabase.update(DBHelper.SEARCH_RECORD_TABLE,contentValues,"rowid="+bean.getSh_id(),null);

    }

    public void delete(SearchHistroyBean bean)
    {
        mSQLiteDatabase.delete(DBHelper.SEARCH_RECORD_TABLE, "rowid=" + bean.getSh_id(), null);
    }

    public void deleteAll(){
        mSQLiteDatabase.delete(DBHelper.SEARCH_RECORD_TABLE,null,null);
    }

    public ArrayList<SearchHistroyBean> fetcheAll()
    {
        ArrayList<SearchHistroyBean> beans=new ArrayList<>();
        String[] query=new String[]{"rowid as _id","name"};
        Cursor query1 = mSQLiteDatabase.query(false, DBHelper.SEARCH_RECORD_TABLE, query, null, null, null, null, "_id desc", null);

        SearchHistroyBean bean=null;
        if (query1.moveToFirst())
        {
            do {
                bean=new SearchHistroyBean();
                bean.setSh_id(query1.getInt(0)+"");
                bean.setSh_name(query1.getString(1));
                beans.add(bean);
            }while (query1.moveToNext());
        }
        query1.close();
        return beans;
    }
}
