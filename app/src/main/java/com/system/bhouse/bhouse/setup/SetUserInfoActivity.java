package com.system.bhouse.bhouse.setup;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.system.bhouse.base.AccountInfo;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.task.bean.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_user_edit)
@OptionsMenu(R.menu.set_value_example)
public class SetUserInfoActivity extends WWBackActivity {

//    final String HOST_USERINFO = Global.HOST_API + "/user/updateInfo";
    @Extra("title")
    String title;
    @Extra("row")
    int row;
    UserObject user;
//    @ViewById
//    TextView value;
    @ViewById
    TextView toolbar_title;
    @ViewById
    Toolbar toolbar_com;

    @AfterViews
    protected final void initSetUserInfoActivity() {
        setSupportActionBar(toolbar_com);

        setActionBarTitle(title);
        user = AccountInfo.loadAccount(this);
        final String hintFormat = "请输入%s";
//        value.setHint(String.format(hintFormat, title));
//        value.setText(getRowValue());
//        value.requestFocus();
    }

//    @OptionsItem
//    void submit() {
////        RequestParams params = new RequestParams();
//
//        setRowValue(value.getText().toString());
//
//        try {
////            params.put("phone", user.phone);
////            params.put("tags", user.tags);
////            params.put("job", user.job);
////            params.put("sex", user.sex);
////            params.put("phone", user.phone);
////            params.put("birthday", user.birthday);
////            params.put("location", user.location);
////            params.put("company", user.company);
////            params.put("slogan", user.slogan);
////            params.put("introduction", user.introduction);
////            params.put("lavatar", user.lavatar);
////            params.put("global_key", user.global_key);
////            params.put("name", user.name);
////            params.put("email", user.email);
////            params.put("id", user.id);
//
//            AccountInfo.saveAccount(this,user);
//            exampleLoadingFinishe(getResources().getString(R.string.loading_str_box));
////            postNetwork(HOST_USERINFO, params, HOST_USERINFO);
//        } catch (Exception e) {
//            showMiddleToast(e.toString());
//        }
//    }

    @Override
    protected void OnNextUiChange() {
        super.OnNextUiChange();
        finish();
    }

    //    @Override
//    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
//        if (code == 0) {
//            showButtomToast("修改成功");
//            setResult(Activity.RESULT_OK);
//            AccountInfo.saveAccount(this, user);
//            finish();
//        } else {
//            showErrorMsg(code, respanse);
//        }
//    }

    private String getRowValue() {
        String returnValue = "";
        switch (row) {

            case UserDetailEditActivity.USERINFO_NAME:
                //昵称
                returnValue = user.name;
                break;

            case UserDetailEditActivity.USERINFO_SEX:
                //性别
                break;

            case UserDetailEditActivity.USERINFO_BIRTHDAY:
                //生日
                break;

            case UserDetailEditActivity.USERINFO_LOCATION:
                //所在地
                break;

            case UserDetailEditActivity.USERINFO_SLOGAN:
                //座右铭
                returnValue = user.slogan;
                break;

            case UserDetailEditActivity.USERINFO_COMPANY:
                //公司
                returnValue = user.company;
                break;

            case UserDetailEditActivity.USERINFO_JOB:
                //职位
                break;

            case UserDetailEditActivity.USERINFO_TAGS:
                //个性标签
                break;
        }
        return returnValue;
    }

    private void setRowValue(String rowValue) {
        switch (row) {
            case UserDetailEditActivity.USERINFO_NAME:
                //昵称
                user.name = rowValue;
                break;

            case UserDetailEditActivity.USERINFO_SEX:
                //性别
                break;

            case UserDetailEditActivity.USERINFO_BIRTHDAY:
                //生日
                break;

            case UserDetailEditActivity.USERINFO_LOCATION:
                //所在地
                break;

            case UserDetailEditActivity.USERINFO_SLOGAN:
                //座右铭
                /*if(rowValue.length() > 50){
                    showButtomToast("座右铭太长了，请不要超过50个字");
                    result = false;
                }else{
                    user.slogan = rowValue;
                }*/
                user.slogan = rowValue;
                break;

            case UserDetailEditActivity.USERINFO_COMPANY:
                //公司
                user.company = rowValue;
                break;

            case UserDetailEditActivity.USERINFO_JOB:
                //职位
                break;

            case UserDetailEditActivity.USERINFO_TAGS:
                //个性标签
                break;
        }
    }
}
