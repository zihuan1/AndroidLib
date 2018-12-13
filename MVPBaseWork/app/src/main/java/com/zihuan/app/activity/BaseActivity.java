package com.zihuan.app.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.zihuan.app.R;
import com.zihuan.app.UserManager;
import com.zihuan.app.interfaces.BaseView;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.presenter.BasePresenter;
import com.zihuan.app.u.U;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity implements BaseView {
    public String uid;
    public String token = "";
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!U.CheckNetworkConnected()) {
            U.ShowToast("请检测网络后再试");
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        presenter = createPresenter();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNoNull(UserManager.getInstance().getUserData().getUid())) {
            uid = UserManager.getInstance().getUserData().getUid();
            token = UserManager.getInstance().getUserData().getToken();
        }
    }


    public boolean isNoNull(String str1) {
        if (!TextUtils.isEmpty(str1) && !str1.equals("null")) {
            return true;
        }
        return false;
    }

    public boolean modelIsNotNull(BaseBeanModel model) {
        if (model == null || model.getStateCode() != 200) {
            return false;
        }
        return true;
    }

    public boolean listIsNoNull(List list) {
        if (!list.isEmpty()) {
            return true;
        }
        return false;
    }

    //    数据是否为空
    public boolean dataIsNotNull(BaseBeanModel model) {
        if (model.getData() == null) return false;
        return modelIsNotNull(model) ? listIsNoNull((List) model.getData()) : false;
    }

    public boolean entityIsNotNull(BaseBeanModel model) {
        return modelIsNotNull(model) ? model.getData() != null ? true : false : false;
    }

    public void back(View view) {
        finish();
    }

    //    空置点击事件
    public void emptyClick(View view) {
    }

    // 获取布局文件
    public abstract int getLayoutId();

    // 初始化view
    public abstract void initView();

    // 数据加载
    public abstract void initData();

    // 创建逻辑层
    protected abstract P createPresenter();

}
