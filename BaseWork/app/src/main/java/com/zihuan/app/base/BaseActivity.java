package com.zihuan.app.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.zihuan.app.MainApplication;
import com.zihuan.app.R;
import com.zihuan.utils.cmhlibrary.CommonHeplerKt;

import org.greenrobot.eventbus.EventBus;

import static com.zihuan.utils.cmhlibrary.CommonHeplerKt.ShowToast;


public abstract class BaseActivity extends SuperActivity {
    public String uid;
    public String token = "";
    protected boolean initLazy;
    protected boolean layoutLazy;
    public int count = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CommonHeplerKt.checkNetworkConnected()) {
           ShowToast("请检测网络后再试");
        }
//        设置系统默认语言
//        Configuration configuration = getResources().getConfiguration();
//        configuration.locale = Locale.ENGLISH;
//        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        if (!layoutLazy) {
            setContentView(getLayoutId());
//            Logger.INSTANCE.tag("加载布局");
        } else {
//            Logger.INSTANCE.tag("不加载布局");
        }
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.color_3000));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getUid();
        if (!initLazy) {
            initView();
            initData();
        }
//        Logger.INSTANCE.tag(getComponentName().getClassName());
//        LogUtils.error(getClass(),getComponentName().getClassName());
        MainApplication.getInstance().addActivity(this);
        Logger.e("当前页面"+getComponentName().getClassName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUid();
    }




    public void getUid() {
//        uid = UserManager.INSTANCE.getUserData().getUser_id();
//        token = U.MD5(UserManager.INSTANCE.getUserData().getUser_token() + "_" + Constant.INSTANCE.getAPI_KEY());
//        Logger.INSTANCE.tag("User_id " + uid);
//        Logger.INSTANCE.tag("User_token " + token);
    }

    public boolean isLogin() {
        if (isNoNull(uid)) {
            return true;
        }
        return false;
    }

    public boolean isNotLogin() {
        if (!isNoNull(uid)) {
//            startSuperActivity(LoginActivity.class);
            return true;
        }
        return false;
    }

    public void enableEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    // 获取布局文件
    public abstract int getLayoutId();

    // 初始化view
    public abstract void initView();

    // 数据加载
    public abstract void initData();

}
