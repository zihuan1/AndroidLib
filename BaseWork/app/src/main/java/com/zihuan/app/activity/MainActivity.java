package com.zihuan.app.activity;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zihuan.app.R;
import com.zihuan.app.adapter.ViewPAdapter;
import com.zihuan.app.fragment.Fm_1;
import com.zihuan.app.fragment.Fm_2;
import com.zihuan.app.fragment.Fm_3;
import com.zihuan.app.fragment.Fm_4;
import com.zihuan.app.model.Test;
import com.zihuan.app.u.Logger;
import com.zihuan.app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    NoScrollViewPager m_vpMain;
    ViewPAdapter mVPAdapter;
    @BindView(R.id.page_bar)
    PageNavigationView pageBar;

    private int anInt;
    NavigationController mNavigationController;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        int color = getResources().getColor(R.color.colorPrimary);
        int def = getResources().getColor(R.color.color_8888);

        mNavigationController = pageBar.material()
                .addItem(R.mipmap.ic_launcher, "首页", color)
                .addItem(R.mipmap.ic_launcher, "第二页", color)
                .addItem(R.mipmap.ic_launcher, "第三页", color)
                .addItem(R.mipmap.ic_launcher, "我的", color)
                .setDefaultColor(def)
                .build();
//        mNavigationController = pageBar.custom()
//                .addItem(newItem(R.mipmap.ic_launcher, "首页", color))
//                .addItem(newItem(R.mipmap.ic_launcher, "第二页", color))
//                .addItem(newItem(R.mipmap.ic_launcher, "第三页", color))
//                .addItem(newItem(R.mipmap.ic_launcher, "我的", color))
////                .setDefaultColor(def)
//                .build();
        List<Fragment> fragments = new ArrayList<>();
        Fm_1 home = new Fm_1();
        Fm_2 page1 = new Fm_2();
        Fm_3 page2 = new Fm_3();
        Fm_4 page3 = new Fm_4();
        fragments.add(home);
        fragments.add(page1);
        fragments.add(page2);
        fragments.add(page3);
        mVPAdapter = new ViewPAdapter(getSupportFragmentManager(), fragments);
        m_vpMain.setAdapter(mVPAdapter);
        mNavigationController.setupWithViewPager(m_vpMain);
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
        Test test = Test.builder().name("蛤蛤").build();
        Logger.tag(test.toString());
    }

    @Override
    public void initData() {

    }


    //创建一个Item
    private BaseTabItem newItem(int drawable, String text, int checkedDrawable) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }


}
