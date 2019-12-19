package com.zihuan.app.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jaeger.library.StatusBarUtil;
import com.tripsdiy.app.u.Logger;
import com.zihuan.app.Constant;
import com.zihuan.app.MainApplication;
import com.zihuan.app.R;
import com.zihuan.app.UserManager;
import com.zihuan.app.u.U;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseActivity extends SuperActivity {
    public String uid;
    public String token = "";
    public boolean layoutLazy;//布局延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!U.CheckNetworkConnected()) {
            U.ShowToast("请检测网络后再试");
        }
        if (!layoutLazy) {
            setContentView(getLayoutId());
        }
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.color_3000));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getUid();
        if (!layoutLazy) {
            initView();
            initData();
        }
        Logger.INSTANCE.tag(getComponentName().getClassName());
        MainApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUid();
    }

    public void getUid() {
        uid = UserManager.INSTANCE.getUserData().getUid();
        token = U.MD5(UserManager.INSTANCE.getUserData().getToken() + "_" + Constant.INSTANCE.getAPI_KEY());
    }

    public boolean isLogin() {
        if (isNoNull(uid)) {
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

    /***
     * 权限检测
     * @param per
     * @return
     */
    public boolean checkPermission(String... per) {
        if (PermissionsUtil.hasPermission(this, per)) {
            return true;
        } else {
            PermissionsUtil.requestPermission(this, permissionListener, per);
        }
        return false;
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void permissionGranted(@NonNull String[] permission) {

        }

        @Override
        public void permissionDenied(@NonNull String[] permission) {

        }
    };


    // 获取布局文件
    public abstract int getLayoutId();

    // 初始化view
    public abstract void initView();

    // 数据加载
    public abstract void initData();

}
