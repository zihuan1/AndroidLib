package com.zihuan.custon.view;

import android.content.Context;
import android.widget.TextView;

import com.zihuan.custon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SpinnerAdapter extends XRecyclerAdapter {

    @BindView(R.id.tv_text)
    TextView tvText;
    private List<String> mList = new ArrayList();


    public SpinnerAdapter(Object object, ViewOnItemClick viewOnItemClick, List<String> list) {
        super(object, viewOnItemClick);
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void convert(XRecyclerViewHolder holder, int position, Context context) {
//        Logger.tag("1111");
        tvText.setText(mList.get(position));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.spinner_text;
    }
}
