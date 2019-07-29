package com.zihuan.app.task;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.tripsdiy.app.u.DataUtils;
import com.tripsdiy.app.u.Logger;
import com.zihuan.app.model.BaseBeanModel;

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

    @Override
    public Object parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }

    //    成功回调
    @Override
    public void onSuccess(Object json, Call call, Response response) {
        try {
            BaseBeanModel obg = null;
            if (mClassType != null) {
                try {
                    obg = mGson.fromJson(json.toString(), mClassType);
                } catch (Exception e) {
                    Logger.INSTANCE.tag("数据解析错误" + e.toString());
                }
            }
            if (DataUtils.INSTANCE.modelIsNotNull(obg)) {
                if (!DataUtils.INSTANCE.entityIsNotNull(obg)) {
//                if (!DataUtils.INSTANCE.entityIsNotNull(obg) || !DataUtils.INSTANCE.dataIsNotNull(obg)) {
                    callBack.onEmptyData(obg);
                    Logger.INSTANCE.tag("无数据回调");
                } else {
                    callBack.onHttpSuccess(obg);
//                    Logger.INSTANCE.tag("有数据回调");
                }
            } else {
                switch (obg.getStateCode()) {
                    case 1002://token 验证失败
                        break;
                }
                callBack.onFail(obg.getStateCode(), obg.getErrorMsg());
            }
        } catch (Exception e) {
            Logger.INSTANCE.e("Exception", e.toString());
            callBack.onHttpError(e);
        }
        Logger.INSTANCE.tag("\n-------------------接口返回数据开始-------------------" +
                "\n接口地址 " + response.request().url() + "\n接口数据 " + json.toString() + "\n" +
                "-------------------接口返回数据结束-------------------");
    }

    // 失败后的回调
    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        callBack.onHttpError(e);//用来取消刷新动作
        try {
            Logger.INSTANCE.e("onError", e.toString()
                    + "\n " + response.request().url()
                    + "\n " + response.code());
        } catch (Exception e1) {
            Logger.INSTANCE.tag("出错了");
        }

    }

}
