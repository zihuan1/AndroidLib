package com.zihuan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.onitemclick.ViewOnItemClick;
import com.jcodecraeer.xrecyclerview.onitemclick.ViewOnItemLongClick;
import com.jcodecraeer.xrecyclerview.xrecycleradapter.XrecyclerViewHolder;
import com.zihuan.app.u.Logger;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public abstract class XrecyclerAdapter extends RecyclerView.Adapter<XrecyclerViewHolder> {
    public List datas = null;
    ViewOnItemClick onItemClick;
    ViewOnItemLongClick longClick;
    Context mContext;
    int mRes;//资源文件

    public XrecyclerAdapter(List datas, int res, Context context) {
        mRes = res;
        this.datas = datas;
        mContext = context;
        this.onItemClick = (ViewOnItemClick) mContext;
        this.longClick = (ViewOnItemLongClick) mContext;
    }
    public XrecyclerAdapter(List datas, int res, Context context, ViewOnItemClick onItemClick1, ViewOnItemLongClick onItemLongClick) {
        mRes = res;
        this.datas = datas;
        mContext = context;
        this.onItemClick =onItemClick1;
        this.longClick =onItemLongClick;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public XrecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mRes, viewGroup, false);
        XrecyclerViewHolder holder = new XrecyclerViewHolder(view, onItemClick, longClick);
        return holder;
//        return new ViewHolder(view);
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(XrecyclerViewHolder viewHolder, int position) {
//        TextView view = (TextView) viewHolder.getView(R.id.text);
//        view.setText(datas.get(position));
        Logger.tag("+++++++++++++" + position);
        convert(viewHolder, position, mContext);
    }

    public abstract void convert(XrecyclerViewHolder holder, int position, Context context);


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
//    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
//        public TextView mTextView;
//        public ViewHolder(View view){
//            super(view);
//            mTextView = (TextView) view.findViewById(R.id.text);
//            view.setOnClickListener(this);
//            view.setOnLongClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            Logger.tag("点击事件");
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            Logger.tag("长按事件");
//            return true;
//        }
//    }
}
