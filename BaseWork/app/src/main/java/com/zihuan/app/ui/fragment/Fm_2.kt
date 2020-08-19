package com.zihuan.app.ui.fragment

import android.Manifest
import android.content.pm.ActivityInfo
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zihuan.app.R
import com.zihuan.app.adapter.DemoAdapter
import com.zihuan.app.model.UserEntity
import com.zihuan.utils.cmhlibrary.requestEasyPermission
import com.zihuan.view.completelibrary.SimpleRefreshListener
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
                .setLoadListener(object : SimpleRefreshListener() {
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        test()
                    }

                    override fun onLoadMore(refreshLayout: RefreshLayout) {
                        test()
                    }

                })
                .setData(mDemoData)

        tvTest.setOnClickListener {
            requestEasyPermission(*RW_EXTERNAL) {
                Matisse.from(this)
                        .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                        .countable(true)
                        .maxSelectable(1)
                        .gridExpectedSize(300)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        //.originalEnable(true)//是否显示原图
                        .capture(true) //是否提供拍照功能
                        .captureStrategy(CaptureStrategy(true, "com.zihuan.app.fileprovider"))
                        .forResult(100)
            }
        }

    }

    val RW_EXTERNAL = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    fun test() {
        Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    sr_layout.finishRefresh()
                    sr_layout.finishLoadMore()
                }
    }
}
