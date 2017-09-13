package com.jcodecraeer.xrecyclerview.xrecycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.onitemclick.ViewOnItemClick;
import com.jcodecraeer.xrecyclerview.onitemclick.ViewOnItemLongClick;

/**
 */
public class XrecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//    public static TextView mTextView;

    View view;
    ViewOnItemClick onItemClick;
    ViewOnItemLongClick longClick;
    public XrecyclerViewHolder(View view, ViewOnItemClick onItemClick, ViewOnItemLongClick longClick) {
        super(view);
        this.view=view;
//        mTextView = (TextView) view.findViewById(R.id.text);
        this.onItemClick=onItemClick;
        this.longClick=longClick;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onItemClick!=null){
            onItemClick.setOnItemClickListener(v,getPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClick!=null){
            longClick.setOnItemLongClickListener(v,getPosition());
        }
        return true;
    }

    public View getView(int id){
        return view.findViewById(id);
    }
}
