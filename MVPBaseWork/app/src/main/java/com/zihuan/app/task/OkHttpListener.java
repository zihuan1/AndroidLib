package com.zihuan.app.task;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okhttputils.callback.AbsCallback;
import com.zihuan.app.interfaces.RequestCallBack;
import com.zihuan.app.model.DataListModel;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.model.UserModel;
import com.zihuan.app.u.Logger;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 */
public class OkHttpListener extends AbsCallback {

    RequestCallBack callBack;
    int m_What;
    Object m_Object;
    Gson mGson = new Gson();

    public OkHttpListener(RequestCallBack callBack, int what, Object object) {
        this.callBack = callBack;
        m_What = what;
        m_Object = object;
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
        String json=mGson.toJson(userModel);
//        try {
            m_Object = mGson.fromJson(json, m_Object.getClass());
//        } catch (JsonSyntaxException e) {
//            Logger.tag("JsonSyntaxException " + e.toString());
//        }
//        m_Object = userModel;
        Message message = Message.obtain();
        message.obj = m_Object;
        message.what = m_What;
        callBack.onHttpSuccess(message);
        Logger.tag("接口返回数据 " + o.toString());
    }


    // 失败后的回调
    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        callBack.onHttpError(e);//用来取消刷新动作
    }

}
