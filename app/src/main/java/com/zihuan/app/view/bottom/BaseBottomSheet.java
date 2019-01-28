package com.zihuan.app.view.bottom;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.zihuan.app.R;
import com.zihuan.app.activity.BaseActivity;

import butterknife.OnClick;
import lombok.NonNull;

public class BaseBottomSheet {


    BottomSheetDialog mSheetDialog;
    Context mContext;


    public BaseBottomSheet(@NonNull BaseActivity context) {
        mContext = context;
        mSheetDialog = new BottomSheetDialog(context);

    }

    public void setView(View view) {
        mSheetDialog.setContentView(view);
        mSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(mContext.getResources().getColor(R.color.color_0000));
    }


    public void showView() {
        mSheetDialog.show();
    }

    public void dismissView() {
        mSheetDialog.dismiss();
    }

}
