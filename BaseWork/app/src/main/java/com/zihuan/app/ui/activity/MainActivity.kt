package com.zihuan.app.ui.activity


import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.jaeger.library.StatusBarUtil
import com.zihuan.app.R
import com.zihuan.app.adapter.ViewPAdapter
import com.zihuan.app.base.BaseActivity
import com.zihuan.app.model.EventData
import com.zihuan.app.ui.fragment.Fm_2
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this@MainActivity, null)
        val fragments = ArrayList<Fragment>()
        fragments.add(Fm_2())
        fragments.add(Fm_2())
        fragments.add(Fm_2())
        fragments.add(Fm_2())
        vp_main.adapter = ViewPAdapter(supportFragmentManager, fragments)
        //        预加载3个页面
        vp_main.offscreenPageLimit = 5
        bnve.setupWithViewPager(vp_main)
        bnve.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
        bnve.isItemHorizontalTranslationEnabled = false
        vp_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                }
            }
        })
        enableEventBus()
    }


    override fun initData() {
    }

    @Subscribe
    fun onEventMainThread(ed: EventData) {
        when (ed.what) {
        }
    }


    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                exitTime = System.currentTimeMillis()
                toast("再按一次退出")
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}

