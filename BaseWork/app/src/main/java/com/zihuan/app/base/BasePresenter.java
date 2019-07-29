package com.zihuan.app.base;


import com.zihuan.app.task.RequestCallBack;

import java.lang.ref.WeakReference;
import java.util.Map;


public abstract class BasePresenter<V extends BaseView, A extends BaseActivity> {

    public V baseView;

    public A mActivity;
    private WeakReference<V> mViewRef;

    public String uid;
    public String token;

    public void attachView(V baseView) {
        mActivity = (A) (this.baseView = baseView);
        mViewRef = new WeakReference(baseView);
        uid = mActivity.uid;
        token = mActivity.token;
    }

    // 销毁
    public void detachView() {
        baseView = null;
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public V getBaseView() {
        return baseView;
    }


    public void getOkHttpJsonRequest(String url, Map<String, String> map, RequestCallBack callBack) {
        mActivity.getOkHttpJsonRequest(url, map, callBack);
    }

}
