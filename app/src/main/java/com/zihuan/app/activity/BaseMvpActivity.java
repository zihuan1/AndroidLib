package com.zihuan.app.activity;

import android.os.Bundle;

import com.zihuan.app.fw.BaseView;
import com.zihuan.app.fw.BasePresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    public P presenter;
    public boolean mLazy;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initLazy = true;
        if (mLazy) {
            layoutLazy = true;
        }
        super.onCreate(savedInstanceState);
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] actualTypeArguments = type.getActualTypeArguments();
        Class<P> tClass = (Class<P>) actualTypeArguments[0];
        presenter = presenterFactory(tClass);
        presenter.attachView(this);
        if (!mLazy) {
            initView();
            initData();
        }
    }


    //presenter生产者
    public <P extends BasePresenter> P presenterFactory(Class<P> tClass) {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
