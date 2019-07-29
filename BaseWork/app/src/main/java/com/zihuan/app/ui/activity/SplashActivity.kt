package com.zihuan.app.ui.activity

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView

import com.jaeger.library.StatusBarUtil
import com.zihuan.app.R
import com.zihuan.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * 开屏页
 */
class SplashActivity : BaseActivity() {


    internal var anInt = 4
    internal var runnable: Runnable = object : Runnable {
        override fun run() {
            anInt--
            if (anInt > 0) {
                handler.postDelayed(this, 1000)
                tv_time!!.text = "跳过 | $anInt"
            } else {
                tv_time!!.performClick()
            }
        }
    }


    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    this.removeCallbacks(runnable)
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                2 -> {
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        StatusBarUtil.setTransparentForImageView(this, null)
        splash_root!!.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        val animation = AlphaAnimation(0.3f, 1.0f)
        animation.duration = sleepTime.toLong()
        splash_root!!.startAnimation(animation)
    }

    override fun initData() {
        handler.post(runnable)
    }


    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.tv_time -> handler.sendEmptyMessage(1)
        }
    }

    private val sleepTime = 3000
}
