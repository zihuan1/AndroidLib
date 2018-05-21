package com.zihuan.app.adapter;

import android.content.Context;
import android.widget.TextView;


import com.zihuan.app.R;
import com.zihuan.app.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class EmptyAdapter extends XrecyclerAdapter {
    @Bind(R.id.tv_name)
    TextView mtvFile;
    private List<UserEntity> mList = new ArrayList();

    public EmptyAdapter(List datas, Object context) {
        super(datas, context);
        mList.addAll(datas);
    }

    public void updata(List list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() : 0;
    }

    private String name;

    @Override
    public void convert(XrecyclerViewHolder holder, int position, Context context) {
        name = mList.get(position).getUserName();
        mtvFile.setText(name);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_base_layout;
    }
}
