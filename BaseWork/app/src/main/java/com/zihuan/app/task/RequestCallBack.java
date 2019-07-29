package com.zihuan.app.task;

public interface RequestCallBack<T> {
    //请求成功
    void onHttpSuccess(T data);

    //请求失败状态处理
    void onFail(int statusCode, String errorMsg);

    //请求失败
    void onHttpError(Exception err);

    //    空数据回调
    void onEmptyData(T data);

}
