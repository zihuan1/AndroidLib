package com.zihuan.app.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.zihuan.app.R;
import com.zihuan.app.UserManager;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.U;

import butterknife.ButterKnife;

public abstract class BaseActivity extends SuperActivity {
    public String uid;
    public String token = "";
    public boolean initLazy;
    public boolean layoutLazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!U.CheckNetworkConnected()) {
            U.ShowToast("请检测网络后再试");
        }
        if (!layoutLazy) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
            Logger.tag("加载布局");
        }else {
            Logger.tag("不加载布局");
        }
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (!initLazy) {
            initView();
            initData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNoNull(UserManager.getInstance().getUserData().getUid())) {
            uid = UserManager.getInstance().getUserData().getUid();
            token = UserManager.getInstance().getUserData().getToken();
        }
    }


    // 获取布局文件
    public abstract int getLayoutId();

    // 初始化view
    public abstract void initView();

    // 数据加载
    public abstract void initData();


}
