package com.zihuandrag.app;

import android.content.Context;
import android.widget.TextView;

public class RightAdapter extends XRecyclerAdapter {
    public RightAdapter(Object object) {
        super(object);
    }

    @Override
    public void convert(XRecyclerViewHolder holder, int position, Context context) {
        LeftEntity entity = (LeftEntity) baseDatas.get(position);
        TextView tv_name = (TextView) holder.getView(R.id.tv_name);
        tv_name.setText(entity.getName());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.rv_right;
    }
}
