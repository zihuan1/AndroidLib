package com.zihuan.app.task;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.zihuan.app.fw.RequestCallBack;
import com.zihuan.app.model.DataListModel;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 */
public class OkHttpListener extends AbsCallback {

    RequestCallBack callBack;
    int m_What;
    Type mClassType;
    Gson mGson = new Gson();

    public OkHttpListener(RequestCallBack callBack, int what, Type type) {
        this.callBack = callBack;
        m_What = what;
        mClassType = type;
    }

    @Override
    public Object parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }

    //    成功回调
    @Override
    public void onSuccess(Object o, Call call, Response response) {
        DataListModel userModel = new DataListModel();
        List<UserEntity> list = new ArrayList<>();
        UserEntity entity = new UserEntity();
        entity.setId(1);
        UserEntity entity1 = new UserEntity();
        entity1.setId(2);
        UserEntity entity2 = new UserEntity();
        entity2.setId(3);
        list.add(entity);
        list.add(entity1);
        list.add(entity2);
        userModel.setData(list);
        String json = mGson.toJson(userModel);
        Object  obj = mGson.fromJson(json, mClassType);

        TaskData data = new TaskData();
        data.setWhat(m_What);
        data.setData(obj);
        callBack.onHttpSuccess(data);
        Logger.tag("接口返回数据 " + o.toString());
    }

    // 失败后的回调
    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        callBack.onHttpError(e);//用来取消刷新动作
    }

}
