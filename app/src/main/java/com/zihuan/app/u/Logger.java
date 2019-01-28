package com.zihuan.app.u;

import android.util.Log;

public class Logger {

	
	static int i = 2;

	public static void i(String tag, String msg) {
		if (i > 0) {
			Log.i(tag, msg == null ? "" : msg+"");
		}
	}
	
	public static void e(String tag, String msg) {
		if (i > 1) {
			Log.e(tag, msg == null ? "" : msg+"");
		}
	}
	public static void tag( String msg) {
		if (i > 1) {
			Log.e("TAG", msg == null ? "" : msg+"");
		}
	}
}