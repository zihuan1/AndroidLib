package com.zihuan.app;


import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.U;

public class UserManager {

    public UserEntity userData = new UserEntity();

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
        U.savePreferences("uid",userData.getUid());
        U.savePreferences("token",userData.getToken());
    }


}
