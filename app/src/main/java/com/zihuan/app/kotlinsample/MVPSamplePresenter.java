package com.zihuan.app.kotlinsample;


import com.zihuan.app.fw.BasePresenter;
import com.zihuan.app.task.TaskData;
import com.zihuan.app.u.Logger;

import java.util.Map;

import io.reactivex.Observable;


public class MVPSamplePresenter extends BasePresenter<MVPSampleView,MVPSampleActivity> {


//    public MVPSamplePresenter(MVPSampleView baseView) {
//        super(baseView);
//    }

    @Override
    public void onRequestSuccess(TaskData data) {
        Logger.tag("请求成功");
//        DataListModel model = (DataListModel) msg.obj;
//        List<UserEntity> list = new ArrayList<>();
//        list.addAll(model.getData());
//        baseView.onLoginSucc(model);
        Observable observable =  data.getObservable();
        baseView.onLoginSucc(observable);
    }

    public void login(Map<String, String> map) {
        getOkHttpJsonRequest("http://xinglian3.1bu2bu.com/api/getTvModule.php", map,  1);
    }


    @Override
    public void onRequestError(Exception err) {

    }

}