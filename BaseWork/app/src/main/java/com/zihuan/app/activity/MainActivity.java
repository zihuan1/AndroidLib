
package com.zihuan.app.activity;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zihuan.app.R;
import com.zihuan.app.adapter.ViewPAdapter;
import com.zihuan.app.fragment.Fm_3;
import com.zihuan.app.fragment.Fm_1;
import com.zihuan.app.fragment.Fm_4;
import com.zihuan.app.fragment.Fm_2;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.vp_main)
    NoScrollViewPager m_vpMain;
    @Bind(R.id.tv_shebei)
    TextView m_tvShebei;
    @Bind(R.id.ll_shebei)
    LinearLayout m_llShebei;
    @Bind(R.id.tv_yujing)
    TextView m_tvYujing;
    @Bind(R.id.tv_guzhang)
    TextView m_tvGuzhang;
    @Bind(R.id.tv_wode)
    TextView m_tvWode;
    ViewPAdapter mVPAdapter;

    private int anInt;
    List<TextView> mViewList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
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
        m_vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        m_llShebei.performClick();
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.ll_shebei, R.id.ll_yujing, R.id.ll_guzhang, R.id.ll_wode})
    public void onClick(View view) {
        for (int i = 0; i < 4; i++) {
            mViewList.get(i).setSelected(false);
            mViewList.get(i).setTextColor(getResources().getColorStateList(R.color.color_8888));
        }
        switch (view.getId()) {
            case R.id.ll_shebei:
                m_vpMain.setCurrentItem(0, false);

                break;
            case R.id.ll_yujing:
                m_vpMain.setCurrentItem(1, false);
                break;
            case R.id.ll_guzhang:
                m_vpMain.setCurrentItem(2, false);
                break;

            case R.id.ll_wode:
                m_vpMain.setCurrentItem(3, false);
                break;
        }
        mViewList.get(anInt).setSelected(true);
        mViewList.get(anInt).setTextColor(getResources().getColorStateList(R.color.color_8888));
    }
}
