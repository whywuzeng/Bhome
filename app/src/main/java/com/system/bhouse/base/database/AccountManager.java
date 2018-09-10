package com.system.bhouse.base.database;

import com.system.bhouse.base.storage.BHPrefrences;

/**
 * Created by Administrator on 2018-09-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base.database
 */

public class AccountManager {
    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 保存用户状态
     *
     * @param state
     */
    public static void setSignState(boolean state) {
        BHPrefrences.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    public static boolean isSignIn() {
        return BHPrefrences.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
