package com.zihuan.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zihuan.app.activity.BaseActivity;
import com.zihuan.app.fragment.BaseFragment;
import com.zihuan.app.xrv.ViewOnItemClick;
import com.zihuan.app.xrv.ViewOnItemLongClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by jianghejie on 15/11/26.
 */
public abstract class XrecyclerAdapter extends RecyclerView.Adapter<XrecyclerViewHolder> {
    public List datas = new ArrayList();
    ViewOnItemClick onItemClick;
    ViewOnItemLongClick longClick;
    Context mContext;
    int mRes;//资源文件

    public XrecyclerAdapter(List datas, int res, Context context) {
        mRes = res;
        this.datas.clear();
        this.datas.addAll(datas);
        mContext = context;
    }

    public XrecyclerAdapter(List datas, int res, Context context, ViewOnItemClick onItemClick1, ViewOnItemLongClick onItemLongClick) {
        mRes = res;
        this.datas.clear();
        this.datas.addAll(datas);
        mContext = context;
        this.onItemClick = onItemClick1;
        this.longClick = onItemLongClick;
    }

    //为butter准备的构造
    public XrecyclerAdapter(List datas, Object object) {
        this.datas.clear();
        this.datas.addAll(datas);
        if (object instanceof BaseFragment) {
            mContext = ((BaseFragment) object).mActivity;
        } else if (object instanceof BaseActivity) {
            mContext = (Context) object;
        }
        if (object instanceof ViewOnItemClick) {
            this.onItemClick = (ViewOnItemClick) object;
        }
        if (object instanceof ViewOnItemLongClick) {
            this.longClick = (ViewOnItemLongClick) object;
        }
    }


    public XrecyclerAdapter(List datas, Context context, ViewOnItemClick onItemClick1, ViewOnItemLongClick onItemLongClick) {
        this.datas.clear();
        this.datas.addAll(datas);
        mContext = context;
        this.onItemClick = onItemClick1;
        this.longClick = onItemLongClick;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public XrecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int res;
        if (mRes != 0) {
            res = mRes;
        } else {
            res = getLayoutResId();
        }

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(res, viewGroup, false);
        XrecyclerViewHolder holder = new XrecyclerViewHolder(view, onItemClick, longClick);

        return holder;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(XrecyclerViewHolder viewHolder, int position) {
        ButterKnife.bind(this, viewHolder.getView());
        convert(viewHolder, position, mContext);
    }

    public abstract void convert(XrecyclerViewHolder holder, int position, Context context);

    public abstract int getLayoutResId();


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas == null || datas.size() == 0 ? 0 : datas.size();
    }


    public boolean isNull(String str) {
        return str == null || str.length() == 0 || "0".equals(str);
    }


}
