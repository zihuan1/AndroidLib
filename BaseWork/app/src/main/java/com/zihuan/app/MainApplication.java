package com.zihuan.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lzy.okhttputils.OkHttpUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

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
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
//            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.colorPrimaryDark);//全局设置主题颜色
            return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
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
    //   获取屏幕信息16比9
    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) mainApplication.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
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
