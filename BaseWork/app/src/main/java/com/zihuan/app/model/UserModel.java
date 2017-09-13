package com.zihuan.app.model;

import java.util.List;

/**
 */
public class UserModel extends BaseBeanModel {
    List<UserEntity>  data;

    public List<UserEntity> getData() {
        return data;
    }

    public void setData(List<UserEntity> data) {
        this.data = data;
    }
}
