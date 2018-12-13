package com.zihuan.app.presenter;

import android.os.Message;

import com.zihuan.app.interfaces.MVPSampleView;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.model.DataListModel;
import com.zihuan.app.model.HahaEntity;
import com.zihuan.app.model.TestEntity;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MVPSamplePresenter extends BasePresenter<MVPSampleView> {

    public MVPSamplePresenter(MVPSampleView baseView) {
        super(baseView);
    }


    public void login(Map<String, String> map) {
        Logger.tag("网络请求");
        setDialogEnable(true);
        getOkHttpJsonRequest("http://xinglian3.1bu2bu.com/api/getTvModule.php", map, new DataListModel<UserEntity>(), this, 1);
    }

    @Override
    public void onRequestSuccess(Message msg) {
        Logger.tag("请求成功");
//        TestEntity entity = (TestEntity) message.obj;
//        baseView.onLoginSucc(entity);
        DataListModel model = (DataListModel) msg.obj;
        List<UserEntity> list = new ArrayList<>();
        list.addAll(model.getData());
        baseView.onLoginSucc(model);
    }

    @Override
    public void onRequestError(Exception err) {

    }

}
