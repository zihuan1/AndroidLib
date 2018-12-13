package com.zihuan.app.activity;

import android.widget.Button;

import com.zihuan.app.R;
import com.zihuan.app.interfaces.MVPSampleView;
import com.zihuan.app.model.DataListModel;
import com.zihuan.app.model.TestEntity;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.presenter.MVPSamplePresenter;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.U;

import junit.framework.Test;

import butterknife.BindView;
import butterknife.OnClick;

public class MVPSampleActivity extends BaseActivity<MVPSamplePresenter> implements MVPSampleView<DataListModel> {

    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    public MVPSamplePresenter createPresenter() {
        return new MVPSamplePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @Override
    public void onLoginSucc(DataListModel entity) {
        Logger.tag(entity.toString());
        U.ShowToast("登录成功");
    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        presenter.login(null);
    }
}

