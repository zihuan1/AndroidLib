package com.zihuan.app.view.bottom;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zihuan.app.R;
import com.zihuan.app.activity.BaseActivity;
import com.zihuan.app.xrv.RecycleLayoutKt;
import com.zihuan.app.xrv.ViewOnItemClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomPopupDialog {
    public PopupWindow mPopupWindow;

    @BindView(R.id.xRecycleView)
    RecyclerView xRecycleView;

    BottomSheetAdapter mBottomAdapter;
    List<String> mFieldList = new ArrayList<>();
    private Context mContext;
    View showAsDropDown;

    @SuppressWarnings("deprecation")
    public BottomPopupDialog(BaseActivity context, View rootView, ViewOnItemClick onItemClick) {
        View view = LayoutInflater.from(context).inflate(R.layout.sheet_list_layout, null);
        ButterKnife.bind(this, view);
        showAsDropDown = rootView;
        mContext = context;
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setContentView(view);
        // 第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值

        RecycleLayoutKt.INSTANCE.initVertical(xRecycleView);
        mBottomAdapter = new BottomSheetAdapter(mFieldList, context);
        xRecycleView.setAdapter(mBottomAdapter);
        mPopupWindow.getContentView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.setFocusable(false);
                mPopupWindow.dismiss();
                return true;
            }
        });
    }

    public void setData(List<String> list) {
        mFieldList.addAll(list);
        mBottomAdapter.update(mFieldList);
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showPopup() {
        mPopupWindow.showAtLocation(showAsDropDown, Gravity.BOTTOM, 0, 0);
    }
}
