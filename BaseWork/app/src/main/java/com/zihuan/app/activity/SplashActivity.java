package com.zihuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zihuan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 开屏页
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.img_splash)
    ImageView splashRoot;

    @BindView(R.id.tv_time)
    TextView tvTime;

    private static final int sleepTime = 3000;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        StatusBarUtil.setTransparentForImageView(this, null);
        splashRoot.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(sleepTime);
        splashRoot.startAnimation(animation);
    }

    @Override
    public void initData() {
        handler.post(runnable);
    }

    int anInt = 4;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            anInt--;
            if (anInt > 0) {
                handler.postDelayed(this, 1000);
                tvTime.setText("跳过 | " + anInt);
            } else {
                tvTime.performClick();
            }
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    handler.removeCallbacks(runnable);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    break;
            }
        }
    };


    @OnClick({R.id.tv_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                handler.sendEmptyMessage(1);
                break;
        }
    }
}
