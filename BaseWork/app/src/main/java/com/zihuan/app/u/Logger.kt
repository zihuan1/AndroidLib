package com.tripsdiy.app.u

import android.util.Log
import com.zihuan.app.BuildConfig

object Logger {

    var i = 2

    fun e(tag: String, msg: Any) {
        if (i > 1 && BuildConfig.DEBUG) {
            Log.e(tag, msg.toString())
        }
    }

    fun tag(msg: Any) {
//        if (i > 1 && BuildConfig.DEBUG) {
        Log.e("TRIPS_TAG", msg.toString())
//        }
    }
}