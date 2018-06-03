package com.system.bhouse.bhouse.task.bean;

import com.system.bhouse.base.App;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-11-8.
 */

public class UserObject implements Serializable{
    //头像
    public String avatar = "";
    //标语
    public String slogan = "";
    //标签
    public String tags = "";

    public String tags_str = "";
    //公司
    public String company = "";
    //全球——key
    public String global_key = "";
    public int id;
    //介绍
    public String introduction = "";
    //工作
    public String job_str = "";

    public String lavatar = "";

    public String location = "";
    public String name = "";
    public String path = "";
    public String phone = "";
    public String birthday = "";
    public long created_at;
    public int fans_count;
    public boolean follow;  // 别人是否关注我
    public boolean followed;
    public int follows_count;
    public int job;
    public int sex;
    public int status;
    public long last_activity_at;
    public long last_logined_at;
    public long updated_at;
    public int tweets_count;
    public String email = "";
    public String pingYin = "";
    public double points_left = 0;
    //验证
    public int email_validation = 0;
    public int phone_validation = 0;
    public String phone_country_code = "+86";

    public boolean isMe()
    {
        return App.sUserObject.id == id;
    }

    public String getFirstLetter() {
        String letter = pingYin.substring(0, 1).toUpperCase();
        if (0 <= letter.compareTo("A") && letter.compareTo("Z") <= 0) {
            return letter;
        }

        return "#";
    }

}
