package com.zihuan.app.base;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


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
