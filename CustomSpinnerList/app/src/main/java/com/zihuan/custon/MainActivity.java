package com.zihuan.custon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zihuan.custon.view.SpinnerScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ss_view)
    SpinnerScrollView ssView;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        List<List<String>> mTexts = new ArrayList<>();
        mTexts.add(Arrays.asList(getResources().getStringArray(R.array.zf1)));
        mTexts.add(Arrays.asList(getResources().getStringArray(R.array.zf2)));
        mTexts.add(Arrays.asList(getResources().getStringArray(R.array.zf3)));
        ssView.setSprinnData(mTexts.get(0));
        View footview = View.inflate(this, R.layout.foot_layot, null);
        ssView.addFootView(footview);
    }

    @OnClick({R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_ok:
                Log.e("数据", ssView.getData().toString());
                break;
        }
    }
}
