package com.zihuan.app.xrv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zihuan.app.xrv.ViewOnItemClick;
import com.zihuan.app.xrv.ViewOnItemLongClick;


/**
 * Created by zihuan on 2016/9/1.
 */
public class XrecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//    public static TextView mTextView;

    View view;
    ViewOnItemClick onItemClick;
    ViewOnItemLongClick longClick;

    public XrecyclerViewHolder(View view, ViewOnItemClick onItemClick, ViewOnItemLongClick longClick) {
        super(view);
        this.view = view;
        this.onItemClick = onItemClick;
        this.longClick = longClick;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public XrecyclerViewHolder(View view, ViewOnItemClick onItemClick, ViewOnItemLongClick longClick, int type) {
        super(view);
        this.view = view;
        this.onItemClick = onItemClick;
        this.longClick = longClick;
        holderType = type;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (onItemClick != null) {
            onItemClick.setOnItemClickListener(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClick != null) {
            longClick.setOnItemLongClickListener(v, getAdapterPosition());
        }
        return true;
    }

    public View getView(int id) {
        return view.findViewById(id);
    }

    int holderType;

    public View getView() {

        return view;
    }

    public int getHolderType() {
        return holderType;
    }

    public void setHolderType(int holderType) {
        this.holderType = holderType;
    }
}
