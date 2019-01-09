package com.zihuan.app.mydialog;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zihuan.app.library.MaterialDialog;

public class MainActivity extends AppCompatActivity {
    MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialDialog = MaterialDialog.materialBuilder(this)
                .setCancelText("取消")
                .setConfirmText("确认")
                .setTitle("提示o(∩_∩)o ")
                .builder()
                .show();
        materialDialog.setOnDialogListener(new MaterialDialog.onDialogListener() {
            @Override
            public void onConfirm(Dialog materialDialog, String etName) {

            }

            @Override
            public void onCancel(Dialog materialDialog) {

            }

            @Override
            public void onOther(Dialog materialDialog) {

            }
        });
    }


    public void showdialg(View view) {
        materialDialog.show();
    }
}
