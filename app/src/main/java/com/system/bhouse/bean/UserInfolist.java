package com.system.bhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016-3-15.
 */
public class UserInfolist {
    private List<UserInfo> userInfos;

    @Override
    public String toString() {
        return "UserInfolist{" +
                "userInfos=" + userInfos +
                '}';
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
}
