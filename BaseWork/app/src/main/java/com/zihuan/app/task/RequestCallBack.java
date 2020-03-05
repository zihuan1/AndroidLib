package com.zihuan.app.task;

/**
 * 网络请求回调
 *
 * @param <T>
 * @author zihuan
 */
public interface RequestCallBack<T> {
    /**
     * 请求成功
     *
     * @param data
     */
    void onHttpSuccess(T data);

    /**
     * 请求失败状态处理
     *
     * @param statusCode
     * @param errorMsg
     */
    void onFail(int statusCode, String errorMsg);

    /**
     * 请求失败
     *
     * @param err
     */
    void onHttpError(Exception err);

    /**
     * 空数据回调
     *
     * @param data
     */
    void onEmptyData(T data);

    /**
     * 不解析返回json字符串
     *
     * @param json
     */
    void onJsonString(String json);

}
