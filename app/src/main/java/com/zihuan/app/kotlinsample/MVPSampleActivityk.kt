package com.zihuanxxjsk.app.sample

import com.zihuan.app.R
import com.zihuan.app.activity.BaseMvpActivity
import com.zihuan.app.kotlinsample.MVPSamplePresenter
import com.zihuan.app.model.DataListModel
import com.zihuan.app.model.UserEntity
import com.zihuan.app.u.U
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*

class MVPSampleActivityk : BaseMvpActivity<MVPSamplePresenter>(), MVPSampleViewk {


    override fun onLoginSucc(kdata: DataListModel<UserEntity>) {

    }

    override fun onLoginSucc(observable: Observable<DataListModel<UserEntity>>) {
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