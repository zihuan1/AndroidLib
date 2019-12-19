package com.zihuan.app.ui.fragment

import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zihuan.app.R
import com.zihuan.app.adapter.DemoAdapter
import com.zihuan.app.model.UserEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_layout.*
import kotlinx.android.synthetic.main.recycle_layout.*
import java.util.concurrent.TimeUnit

/**
 */
class Fm_2 : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.item_layout
    }

    var mDemoData = ArrayList<UserEntity>()
    lateinit var demoAdapter: DemoAdapter
    override fun initView(view: View?) {
        demoAdapter = DemoAdapter(this)
        for (i in 0..30) {
            var entity = UserEntity()
            entity.userName = "昵称$i"
            mDemoData.add(entity)
        }
        rav_layout.buildVerticalLayout(demoAdapter)
                .setLoadListener(object : OnRefreshLoadMoreListener {
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        test()
                    }

                    override fun onLoadMore(refreshLayout: RefreshLayout) {
                        test()
                    }

                })
                .setData(mDemoData)

    }

    fun test() {
        Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    sr_layout.finishRefresh()
                    sr_layout.finishLoadMore()
                }
    }
}
