package com.system.bhouse.base.database;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.system.bhouse.base.App;
import com.system.bhouse.base.storage.BHPrefrences;
import com.system.bhouse.bean.UserInfo;
import com.system.bhouse.bean.UserMidPerm;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/**
 * @author: wuchao
 * @date: 2017/11/26 22:24
 * @desciption:
 */

public class DatabaseManager {

    private DaoSession mDaoSession;
    private UserProfileDao mUserProfileDao;
    private Database db;

    private DatabaseManager() {
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fast_ec.db");

        db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao() {
        return mUserProfileDao;
    }

    private final void removeDao(AbstractDao dao){
        ((UserProfileDao)dao).dropTable(db,true);
    }

    public final void removeUserProfileDao(UserProfileDao dao){
        removeDao(dao);
    }

    public final void createProfileDao()
    {
        UserProfileDao.createTable(db,true);

    }

    public void setAppData(){
        if (TextUtils.isEmpty(BHPrefrences.getAppProfileId()))
        {
            return;
        }
        UserProfile userProfile = null;
        try {
            userProfile = mUserProfileDao.loadByRowId(Long.valueOf(BHPrefrences.getAppProfileId()));
        } catch (SQLiteException e) {
            e.printStackTrace();
            if (e.toString().contains("no such table"))
            {
                createProfileDao();
                return;
            }
        }
        App.USER_INFO = userProfile.getName();
        App.MID = userProfile.getMid();
        App.GSMID = userProfile.getGsmid();
        App.MobileKey = userProfile.getMobilekey();
        App.Filenum = userProfile.getFilenum();
        App.Filesize = userProfile.getFilesize();
        App.Mancompany = userProfile.getMancompany();

        App.usertype = userProfile.getUsertype();
        App.Property = userProfile.getProperty();
        App.IsSub = userProfile.getIssub();
        App.menname = userProfile.getMenname();
        App.mpname = userProfile.getMpname();
    }

    public void UpdateMid(UserMidPerm userMidPerm){
        UserProfile userProfile = mUserProfileDao.loadByRowId(Long.valueOf(BHPrefrences.getAppProfileId()));
        userProfile.setMid(userMidPerm.getMid());
        userProfile.setFatherid(userMidPerm.getFatherid());
        userProfile.setMancompany(userMidPerm.getManCompany());
        userProfile.setProperty(userMidPerm.getProperty());
        userProfile.setIssub(userMidPerm.isIsSub());
        userProfile.setGsmid(userMidPerm.getGsmid());
        mUserProfileDao.insertOrReplace(userProfile);
    }

    public UserInfo getUserInfo(){
        try {
            UserProfile userProfile = mUserProfileDao.loadByRowId(Long.parseLong(BHPrefrences.getAppProfileId()));
            UserInfo info=new UserInfo();
            info.setUsername(userProfile.getName());
            info.setMid(userProfile.getMid());
            info.setGsmid(userProfile.getGsmid());
            info.MobileKey=userProfile.getMobilekey();
            info.Filenum=userProfile.getFilenum();
            info.setFilesizeX((int) userProfile.getFilesize());
            info.setManCompany(userProfile.getMancompany());
            info.mpname=userProfile.getMpname();
            info.setMenname(userProfile.getMenname());
            info.setUsertype(userProfile.getUsertype());
            return info;
        } catch (SQLiteException e) {
            e.printStackTrace();
            if (e.toString().contains("no such column")||e.toString().contains("NullPointerException"))
            {
                removeUserProfileDao(getDao());
            }
            return null;
        }
    }

    public void removeUserInfo(){
         mUserProfileDao.deleteByKeyInTx(Long.parseLong(BHPrefrences.getAppProfileId()));
    }
}
