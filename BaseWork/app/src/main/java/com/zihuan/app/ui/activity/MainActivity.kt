package com.zihuan.app.ui.activity


import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import com.jaeger.library.StatusBarUtil
import com.tripsdiy.app.u.FileUtils
import com.zihuan.app.R
import com.zihuan.app.adapter.ViewPAdapter
import com.zihuan.app.base.BaseActivity
import com.zihuan.app.model.EventData
import com.zihuan.app.u.U
import com.zihuan.app.ui.fragment.Fm_2
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


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
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)
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
                U.ShowToast("再按一次退出")
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}

