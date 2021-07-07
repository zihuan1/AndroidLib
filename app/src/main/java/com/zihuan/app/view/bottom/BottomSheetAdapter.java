package com.zihuan.app.view.bottom;

import android.content.Context;
import android.widget.TextView;

import com.zihuan.app.R;
import com.zihuan.app.xrv.XRecyclerAdapter;
import com.zihuan.app.xrv.XrecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by 1bu2bu-4 on 2017/10/25.
 */

public class BottomSheetAdapter extends XRecyclerAdapter {

    @BindView(R.id.tv_name)
    TextView tvName;
    private ArrayList<String> listModel = new ArrayList<>();

    public BottomSheetAdapter(List datas, Context context) {
        super(datas, context);
        listModel.addAll(datas);
    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }


    public void update(List modelArrayList) {
        listModel.clear();
        listModel.addAll(modelArrayList);
        notifyDataSetChanged();
    }

    @Override
    public void convert(XrecyclerViewHolder holder, final int position, Context context) {
        tvName.setText(listModel.get(position));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.sheet_item_layout;
    }
}
