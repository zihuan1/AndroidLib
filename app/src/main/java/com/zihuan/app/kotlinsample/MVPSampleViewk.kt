package com.zihuanxxjsk.app.sample

import com.zihuan.app.fw.BaseView
import com.zihuan.app.model.DataListModel
import com.zihuan.app.model.UserEntity
import io.reactivex.Observable

interface MVPSampleViewk : BaseView {

    //    如果直接显示数据就实现泛型方法
    fun onLoginSucc(kdata: DataListModel<UserEntity>)

    //需要数据处理就实现rx方法
    fun onLoginSucc(observable: Observable<DataListModel<UserEntity>>)
}