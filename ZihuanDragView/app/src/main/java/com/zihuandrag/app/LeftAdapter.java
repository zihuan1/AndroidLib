package com.zihuandrag.app;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LeftAdapter extends XRecyclerAdapter {
    public LeftAdapter(Object object) {
        super(object);
    }

    @Override
    public void convert(XRecyclerViewHolder holder, int position, Context context) {
        LeftEntity entity = (LeftEntity) baseDatas.get(position);
        TextView tv_name = (TextView) holder.getView(R.id.tv_name);
        TextView tv_count = (TextView) holder.getView(R.id.tv_count);
        RelativeLayout rl_left = (RelativeLayout) holder.getView(R.id.rl_left);
        tv_name.setText(entity.getName());
        tv_count.setText(entity.getCount() + "条");
        if (position == pos) {
            rl_left.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            rl_left.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.rv_left;
    }

    int pos = -1;

    public void updateDrag(int drag) {
        pos = drag;
        notifyDataSetChanged();
    }
}
