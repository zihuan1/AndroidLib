package com.zihuan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zihuan.app.activity.BaseActivity;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {


   public BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) getActivity();

        initView(view);
        return view;
    }

    protected abstract int getLayoutId();

    public abstract void initView(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
