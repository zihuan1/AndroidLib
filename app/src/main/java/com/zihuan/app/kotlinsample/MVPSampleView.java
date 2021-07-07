package com.zihuan.app.kotlinsample;




import com.zihuan.app.fw.BaseView;

import io.reactivex.Observable;

public interface MVPSampleView<T> extends BaseView {
    //    如果直接显示数据就实现泛型方法
    void onLoginSucc(T t);

    //需要数据处理就实现rx方法
    void onLoginSucc(Observable<T> observable);

}
