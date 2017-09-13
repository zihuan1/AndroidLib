package com.zihuan.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zihuan.app.R;
import com.zihuan.app.adapter.ViewPAdapter;
import com.zihuan.app.fragment.Fm_3;
import com.zihuan.app.fragment.Fm_1;
import com.zihuan.app.fragment.Fm_4;
import com.zihuan.app.fragment.Fm_2;
import com.zihuan.app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView m_tvTitle;
    @Bind(R.id.vp_main)
    NoScrollViewPager m_vpMain;
    @Bind(R.id.tv_shebei)
    TextView m_tvShebei;
    @Bind(R.id.ll_shebei)
    LinearLayout m_llShebei;
    @Bind(R.id.tv_yujing)
    TextView m_tvYujing;
    @Bind(R.id.ll_yujing)
    LinearLayout m_llYujing;
    @Bind(R.id.tv_guzhang)
    TextView m_tvGuzhang;
    @Bind(R.id.ll_guzhang)
    LinearLayout m_llGuzhang;
    @Bind(R.id.tv_wode)
    TextView m_tvWode;
    @Bind(R.id.ll_wode)
    public LinearLayout m_llWode;
    @Bind(R.id.rl_title)
    RelativeLayout m_rlTitle;
    ViewPAdapter mVPAdapter;

    private int anInt;
    List<TextView> mViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        initSystemBar(this, R.color.color_6faf);
        initView();
        m_llShebei.performClick();
    }


    private void initView() {
        mViewList.add(m_tvShebei);
        mViewList.add(m_tvYujing);
        mViewList.add(m_tvGuzhang);
        mViewList.add(m_tvWode);
        List<Fragment> fragments = new ArrayList<>();
        Fm_1 fmShebei = new Fm_1();
        Fm_2 fmYijing = new Fm_2();
        Fm_3 fmGuzhang = new Fm_3();
        Fm_4 fm_Wode = new Fm_4();
        fragments.add(fmShebei);
        fragments.add(fmYijing);
        fragments.add(fmGuzhang);
        fragments.add(fm_Wode);
        mVPAdapter = new ViewPAdapter(getSupportFragmentManager(), fragments);
        m_vpMain.setAdapter(mVPAdapter);
//        预加载3个页面
        m_vpMain.setOffscreenPageLimit(3);
        m_vpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                anInt = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.ll_shebei, R.id.ll_yujing, R.id.ll_guzhang, R.id.ll_wode})
    public void onClick(View view) {
        for (int i = 0; i < 4; i++) {
            mViewList.get(i).setSelected(false);
            mViewList.get(i).setTextColor(getResources().getColorStateList(R.color.color_8888));
        }
        if (m_rlTitle.getVisibility() == View.GONE) {
            m_rlTitle.setVisibility(View.VISIBLE);
        }
        switch (view.getId()) {
            case R.id.ll_shebei:
                m_vpMain.setCurrentItem(0, false);
 
                m_tvTitle.setText("1");
                break;
            case R.id.ll_yujing:
                m_vpMain.setCurrentItem(1, false);
                m_tvTitle.setText("2");
                break;
            case R.id.ll_guzhang:
                m_vpMain.setCurrentItem(2, false);
                m_tvTitle.setText("3");
 
                m_tvTitle.setText("设备");
                break;
            case R.id.ll_yujing:
                m_vpMain.setCurrentItem(1, false);
                m_tvTitle.setText("预警");
                break;
            case R.id.ll_guzhang:
                m_vpMain.setCurrentItem(2, false);
                m_tvTitle.setText("故障");
 
                break;
            case R.id.ll_wode:
                m_vpMain.setCurrentItem(3, false);
                m_rlTitle.setVisibility(View.GONE);
                break;
        }
        mViewList.get(anInt).setSelected(true);
        mViewList.get(anInt).setTextColor(getResources().getColorStateList(R.color.color_8888));
    }
}
