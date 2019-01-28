package com.zihuan.app.fw;


import com.lzy.okhttputils.OkHttpUtils;
import com.zihuan.app.activity.SuperActivity;
import com.zihuan.app.task.OkHttpListener;
import com.zihuan.app.task.TaskData;
import com.zihuan.app.u.Logger;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public abstract class BasePresenter<V extends BaseView, A extends SuperActivity> implements RequestCallBack {

    public V baseView;

    // 是否启用rx返回
    public static boolean mRxAndroid;
    public A mActivity;
    private WeakReference<V> mViewRef;

    public void attachView(V baseView) {
        mActivity = (A) (this.baseView = baseView);
        mViewRef = new WeakReference(baseView);
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


    /**
     * @param url
     * @param map
     * @param what 标记值
     */
    public void getOkHttpJsonRequest(String url, Map<String, String> map, int what) {
        Type type = getBaseViewType();
        if (map != null) {
            OkHttpUtils.post(url)
                    .params(map)
//                    .cacheKey("myFriend")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                    .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                    .execute(new OkHttpListener(this, what, type));
            Logger.tag("请求参数 " + map.toString());
        } else {
            OkHttpUtils.post(url)
                    .execute(new OkHttpListener(this, what, type));
        }
    }



    public abstract void onRequestSuccess(TaskData data);

    public abstract void onRequestError(Exception err);

    @Override
    public void onHttpSuccess(TaskData data) {
        if (mRxAndroid) {
            Observable observable = Observable.create(emitter -> {
                emitter.onNext(data.getData());
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            data.setObservable(observable);
            onRequestSuccess(data);
        } else
            onRequestSuccess(data);
    }

    @Override
    public void onHttpError(Exception err) {
        onRequestError(err);
    }


    public BasePresenter setResultRxEnable() {
        mRxAndroid = true;
        return this;
    }


    public Type getBaseViewType() {
        Type type;
        try {
//            获取当前类的接口泛型
            Type[] types = baseView.getClass().getGenericInterfaces();
//        Logger.tag("泛型1 " + baseView.getClass().getGenericInterfaces().toString());
            ParameterizedType parameterized = (ParameterizedType) types[0];
//        Logger.tag("泛型2 " + parameterized.toString());
            type = parameterized.getActualTypeArguments()[0];
//        Logger.tag("泛型3 " + type);
        } catch (Exception e) {
            Logger.tag("泛型异常" + e.toString());
            return null;
        }
        return type;
    }


}
