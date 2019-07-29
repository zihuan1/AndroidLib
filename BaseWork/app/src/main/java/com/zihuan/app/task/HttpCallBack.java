package com.zihuan.app.task;


public class HttpCallBack<T> implements RequestCallBack<T> {


    @Override
    public void onHttpSuccess(T data) {

    }

    @Override
    public void onFail(int statusCode, String errorMsg) {

    }

    @Override
    public void onHttpError(Exception err) {

    }

    @Override
    public void onEmptyData(T data) {

    }
}
