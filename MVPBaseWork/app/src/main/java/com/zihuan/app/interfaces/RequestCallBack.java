package com.zihuan.app.interfaces;

import android.os.Message;

public interface RequestCallBack {
    //请求成功
    void onHttpSuccess(Message message);

    //请求失败
    void onHttpError(Exception err);

}
