package com.zihuandrag.app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zihuan
 */
public abstract class XRecyclerAdapter extends RecyclerView.Adapter<XRecyclerViewHolder> {
    public List baseDatas = new ArrayList();
    ViewOnItemClick onItemClick;
    ViewOnItemLongClick longClick;
    public Context mContext;
    int mRes;//资源文件
    public int mListSize;

    public XRecyclerAdapter(Object object) {
        instanceofObj(object);
    }

    //为butter准备的构造
    public XRecyclerAdapter(List baseDatas, Object object) {
        this.baseDatas.clear();
        this.baseDatas.addAll(baseDatas);
        instanceofObj(object);
    }

    public XRecyclerAdapter(List baseDatas, int res, Context context) {
        mRes = res;
        this.baseDatas.clear();
        this.baseDatas.addAll(baseDatas);
        mContext = context;
    }

    public XRecyclerAdapter(List baseDatas, int res, Context context, ViewOnItemClick onItemClick1, ViewOnItemLongClick onItemLongClick) {
        mRes = res;
        this.baseDatas.clear();
        this.baseDatas.addAll(baseDatas);
        mContext = context;
        this.onItemClick = onItemClick1;
        this.longClick = onItemLongClick;
    }


    public XRecyclerAdapter(Object object, ViewOnItemClick viewOnItemClick) {
        this.baseDatas.clear();
        this.baseDatas.addAll(baseDatas);
        onItemClick = viewOnItemClick;
    }


    private void instanceofObj(Object object) {
        if (object instanceof Activity) {
            mContext = ((Activity) object);
        } else if (object instanceof View) {
            mContext = ((View) object).getContext();
        }
        if (object instanceof ViewOnItemClick) {
            this.onItemClick = (ViewOnItemClick) object;
        }
        if (object instanceof ViewOnItemLongClick) {
            this.longClick = (ViewOnItemLongClick) object;
        }
    }


    public XRecyclerAdapter(List baseDatas, Context context, ViewOnItemClick onItemClick1, ViewOnItemLongClick onItemLongClick) {
        this.baseDatas.clear();
        this.baseDatas.addAll(baseDatas);
        mContext = context;
        this.onItemClick = onItemClick1;
        this.longClick = onItemLongClick;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public XRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int res;
        if (mRes != 0) {
            res = mRes;
        } else {
            res = getLayoutResId();
        }
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(res, viewGroup, false);
        XRecyclerViewHolder holder = new XRecyclerViewHolder(view, onItemClick, longClick);
        return holder;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(XRecyclerViewHolder viewHolder, int position) {
        convert(viewHolder, position, mContext);
    }


    public abstract void convert(XRecyclerViewHolder holder, int position, Context context);

    public abstract int getLayoutResId();

    //获取数据的数量
    @Override
    public int getItemCount() {
        return baseDatas == null || baseDatas.size() == 0 ? 0 : baseDatas.size();
    }


    public boolean isNull(String str) {
        return str == null || str.length() == 0 || "0".equals(str);
    }

    public void update(List list) {
        mListSize = list.size();
        baseDatas.clear();
        baseDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void setAnim(int anim) {
        this.anim = anim;
    }

    private int anim;


}
