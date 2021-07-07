package com.zihuan.app.activity;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zihuan.app.model.BaseBeanModel;

import java.util.List;

public class SuperActivity extends FragmentActivity {

    public boolean isNoNull(String str1) {
        if (!TextUtils.isEmpty(str1) && !str1.equals("null")) {
            return true;
        }
        return false;
    }

    public boolean modelIsNotNull(BaseBeanModel model) {
        if (model == null || model.getStateCode() != 200) {
            return false;
        }
        return true;
    }

    public boolean listIsNoNull(List list) {
        if (!list.isEmpty()) {
            return true;
        }
        return false;
    }

    //    数据是否为空
    public boolean dataIsNotNull(BaseBeanModel model) {
        if (model.getData() == null) return false;
        return modelIsNotNull(model) ? listIsNoNull((List) model.getData()) : false;
    }

    public boolean entityIsNotNull(BaseBeanModel model) {
        return modelIsNotNull(model) ? model.getData() != null ? true : false : false;
    }

    public void back(View view) {
        finish();
    }

    //    空置点击事件
    public void emptyClick(View view) {

    }


    public LoadingDialog dialog;
    public boolean isShow;

    public void showDialog() {
        if (!isFinishing() && !isShow) {
            dialog = new LoadingDialog(this);
            dialog.setLoadingText("加载中...");//设置loading时显示的文字
            dialog.setInterceptBack(false);
            dialog.show();
            isShow = true;
        }
    }

    public void dismissDialog() {
        if (dialog == null || isFinishing()) return;
        dialog.close();
        dialog = null;
        isShow = false;
    }


}
