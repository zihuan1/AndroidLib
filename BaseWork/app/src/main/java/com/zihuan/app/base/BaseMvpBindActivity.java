package com.zihuan.app.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;


public abstract class BaseMvpBindActivity<P extends BasePresenter> extends BaseMvpActivity<P> {
    ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutLazy=true;
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initView();
        initData();
    }

}
