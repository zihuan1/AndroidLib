package com.zihuan.app.interfaces;

import com.zihuan.app.model.TestEntity;

/**
 *
 * todo 泛型
 */


public interface MVPSampleView<T> extends BaseView{
    void onLoginSucc(T t);
}
