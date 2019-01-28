package com.zihuan.app;


import android.text.TextUtils;

import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.LoggerKt;
import com.zihuan.app.u.U;

public class UserManager {

    private UserEntity userData = new UserEntity();

    private UserManager() {
    }

    private static UserManager mUserManager;

    public static UserManager getInstance() {
        if (mUserManager == null) {
            mUserManager = new UserManager();
        }
        return mUserManager;
    }

    public void save(UserEntity user) {
        userData = user;
        U.savePreferences("uid", userData.getUid());
        U.savePreferences("token", userData.getToken());
    }

    public UserEntity getUserData() {
        return userData;
    }

    public void setUserData(UserEntity userData) {
        this.userData = userData;
    }

    //    是否登陆
    public boolean isLogin() {
        if (TextUtils.isEmpty(userData.getUid())) {
            LoggerKt.INSTANCE.tag("-------没有登录-------");
            return false;
        }
        LoggerKt.INSTANCE.tag("--------登录了------" + userData.getUid());
        return true;
    }

}
