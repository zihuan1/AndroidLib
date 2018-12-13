package com.zihuan.app.presenter;


import android.content.Context;
import android.os.Message;

import com.lzy.okhttputils.OkHttpUtils;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zihuan.app.interfaces.BaseView;
import com.zihuan.app.interfaces.RequestCallBack;
import com.zihuan.app.task.OkHttpListener;
import com.zihuan.app.u.Logger;

import java.util.Map;


public abstract class BasePresenter<V extends BaseView> implements RequestCallBack {

    public V baseView;

    // 是否启用加载进度调
    boolean dialogEnable = false, isShow;
    LoadingDialog dialog;

    public BasePresenter(V baseView) {

        this.baseView = baseView;
    }

    public void detachView() {
        baseView = null;
    }

    public V getBaseView() {
        return baseView;
    }

    /**
     * @param url
     * @param map
     * @param obj      数据模型
     * @param callBack 数据回调的handler
     * @param what     hander标记值
     */
    public void getOkHttpJsonRequest(String url, Map<String, String> map, Object obj, RequestCallBack callBack, int what) {
        showDialog();
        if (map != null) {
            OkHttpUtils.post(url)
                    .params(map)
//                    .cacheKey("myFriend")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                    .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                    .execute(new OkHttpListener(callBack, what, obj));
            Logger.tag("请求参数 " + map.toString());
        } else {
            OkHttpUtils.post(url)
                    .execute(new OkHttpListener(callBack, what, obj));
        }
    }

    public abstract void onRequestSuccess(Message message);

    public abstract void onRequestError(Exception err);

    @Override
    public void onHttpSuccess(Message message) {
        dismissDialog();
        onRequestSuccess(message);
    }

    @Override
    public void onHttpError(Exception err) {
        dismissDialog();
        onRequestError(err);
    }

    // dialog是否启用
    public boolean isDialogEnable() {
        return dialogEnable;
    }

    public void setDialogEnable(boolean dialogEnable) {
        this.dialogEnable = dialogEnable;
    }

    //dialog 要换
    public void showDialog() {
        if (dialogEnable && !isShow) {
            dialog = new LoadingDialog((Context) getBaseView());
            dialog.setLoadingText("加载中...");//设置loading时显示的文字
            dialog.setInterceptBack(false);
            dialog.show();
            isShow = true;
        }
    }

    public void dismissDialog() {
        if (dialogEnable) {
            if (dialog == null) return;
            dialog.close();
            dialog = null;
            isShow = false;
        }

    }


}
