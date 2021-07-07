package com.zihuanxxjsk.app.sample

import com.zihuan.app.fw.BasePresenter
import com.zihuan.app.model.DataListModel
import com.zihuan.app.model.UserEntity
import com.zihuan.app.task.TaskData
import com.zihuan.app.u.Logger


class MVPSamplePresenterKt : BasePresenter<MVPSampleViewk, MVPSampleActivityk>() {


    override fun onRequestSuccess(data: TaskData<*>) {
        Logger.tag("请求成功")
        //        DataListModel model = (DataListModel) msg.obj;
        //        List<UserEntity> list = new ArrayList<>();
        //        list.addAll(model.getData());
        //        baseView.onLoginSucc(model);
        when (data.what) {
            1 -> {
                val observable = data.observable as DataListModel<UserEntity>
                baseView.onLoginSucc(observable)
            }
        }
    }

    fun login(map: Map<String, String>) {

        getOkHttpJsonRequest("http://xinglian3.1bu2bu.com/api/getTvModule.php", map, 1)
    }


    override fun onRequestError(err: Exception) {

    }

}

