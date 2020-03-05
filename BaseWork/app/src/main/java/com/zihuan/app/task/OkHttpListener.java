package com.zihuan.app.task;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.orhanobut.logger.Logger;
import com.zihuan.app.BuildConfig;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.u.ZHDataUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */
public class OkHttpListener extends AbsCallback {

    RequestCallBack callBack;
    Type mClassType;
    Gson mGson = new Gson();

    public OkHttpListener(RequestCallBack callBack, Type type) {
        this.callBack = callBack;
        mClassType = type;
    }

    public OkHttpListener(RequestCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public Object parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }

    @Override
    public void onSuccess(Object json, Call call, Response response) {
        try {
            BaseBeanModel obg = null;
            if (BuildConfig.DEBUG)
                if (json.toString().isEmpty()) {
                    new NullPointerException("接口返回的数据为空");
                }
            callBack.onJsonString(json.toString());
            if (mClassType != null) {
                obg = mGson.fromJson(json.toString(), mClassType);
                if (BuildConfig.DEBUG)
                    if (obg == null) {
                        new NullPointerException("BaseBeanModel为空" + json.toString());
                    }
                Logger.e("请求失败" + obg.toString());
                if (obg.getStateCode() != 0) {
                    switch (obg.getStateCode()) {
                        //token 验证失败
                        case 1002:
//                            EventBus.getDefault().post(new EventData(Constant.INSTANCE.getLOGOUT()));
                            break;
                    }
                    Logger.e("请求失败" + obg.toString());
                    callBack.onFail(obg.getStateCode(), obg.getErrorMsg());
                } else if (ZHDataUtils.INSTANCE.entityIsNotNull(obg)) {
                    callBack.onHttpSuccess(obg);
                } else {
                    callBack.onEmptyData(obg);
                    Logger.e("无数据回调");
                }
                if (json.toString().length() < 2000) {
                    Logger.e("2接口地址 " + response.request().url()
                            + "\n接口数据" + new JSONObject(json.toString()).toString(1));
                } else {
                    Logger.e("2接口地址 " + response.request().url()
                            + "\n接口数据" + new JSONObject(json.toString()).toString());
                }
            }

        } catch (Exception e) {
            Logger.e("请求异常" + e.toString()
                    + "\n" + response.request().url()
                    + "\n接口数据" + json.toString());
//            callBack.onHttpError(e);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        //用来取消刷新动作
        callBack.onHttpError(e);
        try {
            Logger.e("onError" + e.toString()
                    + "\n " + response.request().url()
                    + "\n " + response.code());
        } catch (Exception e1) {
            Logger.e("出错了" + e.toString());
        }

    }

}
