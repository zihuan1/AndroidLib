package com.zihuan.app.task;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okhttputils.callback.AbsCallback;
import com.zihuan.app.u.Logger;

import okhttp3.Call;
import okhttp3.Response;

/**
 */
public class OkHttpListener extends AbsCallback {

    Handler m_Handler;
    int m_What;
    Object m_Object;
    Gson mGson = new Gson();

    public OkHttpListener(Handler handler, int what, Object object) {
        this.m_Handler = handler;
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

        try {
            m_Object = mGson.fromJson(o.toString(), m_Object.getClass());
        } catch (JsonSyntaxException e) {
            Logger.tag("JsonSyntaxException "+e.toString());
        }
        Message message = Message.obtain();
        message.obj = m_Object;
        message.what = m_What;
        m_Handler.sendMessage(message);
        Logger.tag("接口返回数据 "+o.toString());
    }


    // 失败后的回调
    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        m_Handler.sendEmptyMessage(666);//用来取消刷新动作
    }

}
