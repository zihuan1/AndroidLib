package com.zihuan.app.kotlinsample

import com.zihuan.app.R
import com.zihuan.app.activity.BaseActivity
import com.zihuan.app.interfaces.MVPSampleView
import com.zihuan.app.model.DataListModel
import com.zihuan.app.model.UserEntity
import com.zihuan.app.presenter.MVPSamplePresenter
import com.zihuan.app.u.LoggerKt
import com.zihuan.app.u.U
import kotlinx.android.synthetic.main.activity_login.*

class MVPSampleActivityKt : BaseActivity<MVPSamplePresenter>(), MVPSampleView<DataListModel<UserEntity>> {

    override fun createPresenter(): MVPSamplePresenter {
        return MVPSamplePresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

    }

    override fun initData() {
        bt_login.text = "你好世界"
        bt_login.setOnClickListener {
            U.ShowToast("你好世界")
            presenter.login(null)
        }
    }

    override fun onLoginSucc(model: DataListModel<UserEntity>?) {
        var modeldata = model
        var listdata = modeldata!!.data
        LoggerKt.tag(listdata.toString())
    }


//    @OnClick(R.id.bt_login)
//    fun onViewClicked(view: View) {
//        when (view.id) {
//            R.id.bt_login -> {
//                U.ShowToast("你好")
//                LoggerKt.tag("你好")
//            }
//        }
//    }
}