package com.zihuan.app.fw;


import com.zihuan.app.task.TaskData;

public interface RequestCallBack {
    //请求成功
    void onHttpSuccess(TaskData data);

    //请求失败
    void onHttpError(Exception err);

}
