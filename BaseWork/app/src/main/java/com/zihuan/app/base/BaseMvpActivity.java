package com.zihuan.app.base;

import android.os.Bundle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    public P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] actualTypeArguments = type.getActualTypeArguments();
        Class<P> tClass = (Class<P>) actualTypeArguments[0];
        presenter = presenterFactory(tClass);
        presenter.attachView(this);
        if (!layoutLazy) {
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
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
