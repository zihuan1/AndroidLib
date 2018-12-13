package com.zihuan.custon.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by zihuan on 2016/9/1.
 */
public class XRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    View view;
    XRecyclerAdapter.ViewOnItemClick onItemClick;
    XRecyclerAdapter.ViewOnItemLongClick longClick;

    public XRecyclerViewHolder(View view, XRecyclerAdapter.ViewOnItemClick onItemClick, XRecyclerAdapter.ViewOnItemLongClick longClick) {
        super(view);
        this.view = view;
        this.onItemClick = onItemClick;
        this.longClick = longClick;
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

    public View getView() {
        return view;
    }

}
