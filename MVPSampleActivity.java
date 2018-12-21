package com.zihuan.app.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zihuan.app.R;
import com.zihuan.app.interfaces.MVPSampleView;
import com.zihuan.app.model.DataListModel;
import com.zihuan.app.presenter.MVPSamplePresenter;
import com.zihuan.app.u.FileUtils;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.U;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.SequenceInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MVPSampleActivity extends BaseActivity<MVPSamplePresenter> implements MVPSampleView<DataListModel> {

    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.et_test)
    EditText etTest;

    @Override
    public MVPSamplePresenter createPresenter() {
        return new MVPSamplePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        etTest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNoNull(etTest.getText().toString())) {
                    Logger.tag("--- " + getCurrentCursorLine(etTest));

                    if (getCurrentCursorLine(etTest) <= 1) {
                        if (etTest.getText().toString().length() == 0) {
                            etTest.setGravity(Gravity.TOP | Gravity.LEFT);
                        } else {
                            etTest.setGravity(Gravity.CENTER | Gravity.LEFT);
                        }
                    } else if (getCurrentCursorLine(etTest) > 1) {
                        etTest.setGravity(Gravity.TOP | Gravity.LEFT);
                    }
                }

            }
        });
    }


    @Override
    public void onLoginSucc(DataListModel entity) {
        Logger.tag(entity.toString());
 
    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
 
    }


    private int getCurrentCursorLine(EditText editText) {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (selectionStart != -1) {
            return layout.getLineForOffset(selectionStart) + 1;
        }
        return -1;
    }

    int rotation;

    public void onViewClicked(View view) {
        switch (rotation) {
            case 0:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
            case 90:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case 180:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
            case 270:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                rotation = -90;
                break;
        }
        rotation += 90;
        Logger.tag("方向 " + rotation);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else {
        }

    }
}

