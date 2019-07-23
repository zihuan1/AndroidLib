package com.zihuan.app.view.bottom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zihuan.app.R;
import com.zihuan.app.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetView extends BaseBottomSheet {

    @BindView(R.id.xRecycleView)
    RecyclerView xRecycleView;
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;
    BottomSheetAdapter mBottomAdapter;
    List<String> mStrList = new ArrayList<>();
    private static BottomSheetView sheetView;


    public BottomSheetView(BaseActivity context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.sheet_list_layout, null);
        ButterKnife.bind(this, view);
        context.iniXrecyclerViewV(xRecycleView);
        mBottomAdapter = new BottomSheetAdapter(mStrList, context);
        xRecycleView.setAdapter(mBottomAdapter);
        setView(view);
    }

    /**
     * 设置数据
     *
     * @param data
     * @return
     */
    public BottomSheetView setData(String... data) {
        if (data != null) {
            mStrList.clear();
            mStrList.addAll(Arrays.asList(data));
            mBottomAdapter.update(mStrList);
        }
        return this;
    }

    /***
     * 显示
     * @return
     */
    public BottomSheetView showDialog() {
        showView();
        return this;
    }

    /***
     *  隐藏
     * @return
     */
    public BottomSheetView dismissDialog() {
        super.dismissView();
        return this;
    }

    /***
     * 取消文字
     * @param text
     * @return
     */
    public BottomSheetView setDismissText(String text) {
        tvDismiss.setText(text);
        return this;
    }

    /***
     *  字体颜色
     * @param color
     * @return
     */
    public BottomSheetView setTextColor(int color) {
        tvDismiss.setTextColor(color);
        return this;
    }

    public static BottomSheetView build(BaseActivity context) {
        return sheetView == null ? sheetView = new BottomSheetView(context) : sheetView;
    }

    @OnClick({R.id.tv_dismiss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dismiss:
                dismissDialog();
                break;
        }
    }
}
