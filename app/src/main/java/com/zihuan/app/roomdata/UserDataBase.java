package com.zihuan.app.roomdata;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zihuan.app.model.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
//1.  @Database 声明这是一个数据库类，其中 entities里面声明你的数据库里究竟包含了哪几个实体；
//第三个属性 exportSchema 比较有意思，Google 建议是传 true，这样可以把 Scheme 导出到一个文件夹里面
// TODO:  数据库必须异步操作
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase sInstance;

    public static UserDataBase getDatabase(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class,
                    "user.db").addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            })
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public static void onDestroy() {
        sInstance = null;
    }

    public abstract UserDao getUserDao();

//    public UserDataBase getDataBase() {
//        return mDataBase == null ? mDataBase = UserDataBase.getDatabase(this) : mDataBase;
//    }


}