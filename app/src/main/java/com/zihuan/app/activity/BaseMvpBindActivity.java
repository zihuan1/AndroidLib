package com.zihuan.app.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.zihuan.app.fw.BasePresenter;


public abstract class BaseMvpBindActivity<P extends BasePresenter> extends BaseMvpActivity<P> {
    ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLazy=true;
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initView();
        initData();
    }

}
