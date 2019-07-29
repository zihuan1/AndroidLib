package com.zihuan.app.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class BaseAssView : FrameLayout {

    constructor(context: Context) : super(context) {
        createView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        createView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        createView()
    }

    lateinit var mActivity: Activity
    private fun createView() {
        var view = View.inflate(context, getLayoutId(), null)
        addView(view)
        mActivity = context as Activity
        initView()
    }


    abstract fun initView()
    abstract fun getLayoutId(): Int

}