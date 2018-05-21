package com.zihuan.app.roomdata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zihuan.app.model.UserEntity;

import java.util.List;


@Dao//1. 顶部使用 @Dao 声明这是 DAO，就是这么简单。
public interface UserDao {
    //2. CRUD 操作全部使用注解声明，需要具体 sql 语句的，直接在注解里书写就好。
//    获取所有
    @Query("select * FROM user_table")
    List<UserEntity> getAllImage();

    @Query("select * FROM user_table WHERE uid =:uid")
    List<UserEntity> getAllSupplier(String uid);

    //添加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    int addUser(UserEntity userEntity);

    //删除
    @Delete()
    void deleteUser(UserEntity userEntity);

}