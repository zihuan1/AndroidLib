package com.zihuan.app.u

import android.util.Log

object LoggerKt {
    var i = 2
    private val TAG = "TAG"
    fun i(tag: String, msg: String?) {
        Log.i(tag, if (i > 0) msg else "")
    }

    fun e(tag: String, msg: String?) {
        Log.e(tag, if (i > 1) msg else "")
    }

    fun tag(msg: String) {
        Log.e(TAG, if (i > 0) msg else "")
    }
}