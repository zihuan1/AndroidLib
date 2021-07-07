package com.zihuan.app.kotlinsample;

import android.widget.Button;


import com.zihuan.app.R;
import com.zihuan.app.activity.BaseMvpActivity;
import com.zihuan.app.model.DataListModel;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.U;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MVPSampleActivity extends BaseMvpActivity<MVPSamplePresenter> implements MVPSampleView<DataListModel<UserEntity>> {

    @BindView(R.id.bt_login)
    Button btLogin;

//    @Override
//    public MVPSamplePresenter createPresenter() {
//        return new MVPSamplePresenter(this)
//                .setDialogEnable()
//                .setResultRxEnable();
//    }

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
        List<UserEntity> list = entity.getData();
        Logger.tag(entity.toString());
        Logger.tag("list " + list.get(0).getId());
        U.ShowToast("登录成功");
    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        presenter.login(null);
    }

    @Override
    public void onLoginSucc(Observable<DataListModel<UserEntity>> observable) {
        observable.map(userEntity -> userEntity.getData())
                .subscribe((Consumer<List>) s -> {
                    Logger.tag("转换后的数据" + s.toString());
                });
    }

}

