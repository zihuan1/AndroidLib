package com.zihuan.app;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

import com.lzy.okhttputils.OkHttpUtils;

import java.util.ArrayList;

/**
 */
public class MainApplication extends Application {
    public static MainApplication mainApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
//        CrashHandler.getInstance().init(this);
        initOkHttp();
        dm = new DisplayMetrics();
    }


    public static MainApplication getInstance() {
        if (mainApplication == null) {
            mainApplication = new MainApplication();
        }
        return mainApplication;
    }

    private static DisplayMetrics dm;

    //   获取屏幕信息16比9
    public static int getScreenWidth(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public ArrayList<Activity> mActivities = new ArrayList<>();

    // 添加activity
    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void remActivity(Activity activity) {
        mActivities.remove(activity);
    }

    private void initOkHttp() {

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);                 //全局的写入超时时间

    }
}
