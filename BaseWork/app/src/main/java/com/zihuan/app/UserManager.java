package com.zihuan.app;


import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.U;

/**
 * Created by 1bu2bu on 2015/12/4.
 * <p>
 * 用户所有行为信息的管理类
 */
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
