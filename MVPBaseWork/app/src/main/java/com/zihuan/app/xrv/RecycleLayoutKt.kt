package com.zihuan.app.xrv

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.zihuan.app.R

object RecycleLayoutKt {
    var mContext: Context? = null

    fun initXrecyclerView(xRecyclerView: XRecyclerView) {
        mContext = xRecyclerView.context
        xRecyclerView.layoutManager = RecycleViewBuilder.getLinLayoutManager(LinearLayoutManager.VERTICAL)
        initXrvConfig(xRecyclerView)
    }

    //横向布局
    fun initHorizontal(recyclerView: RecyclerView): LinearLayoutManager {
        mContext = recyclerView.context
        var layoutManager = RecycleViewBuilder.getLinLayoutManager(LinearLayoutManager.HORIZONTAL)
        recyclerView.layoutManager = layoutManager
        return layoutManager
    }

    //竖向布局
    fun initVertical(recyclerView: RecyclerView) {
        mContext = recyclerView.context
        var layoutManager = initHorizontal(recyclerView)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
    }


    fun initXGrid(xRecyclerView: XRecyclerView, count: Int) {
        mContext = xRecyclerView.context
        xRecyclerView.layoutManager = RecycleViewBuilder.getGridLayoutManager(count)
        initXrvConfig(xRecyclerView)
    }

    fun initGrid(recyclerView: RecyclerView, count: Int) {
        mContext = recyclerView.context
        recyclerView.layoutManager = RecycleViewBuilder.getGridLayoutManager(count)
    }


    //xrv配置
    private fun initXrvConfig(xRecyclerView: XRecyclerView) {
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman)
        xRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey)
        xRecyclerView.setLoadingMoreEnabled(false)
    }

    private object RecycleViewBuilder {
        /***
         * orientation 0横向 1竖向
         */
        fun getLinLayoutManager(orientation: Int): LinearLayoutManager {
            val layoutManager = LinearLayoutManager(mContext)
            layoutManager.orientation = orientation
            return layoutManager
        }

        fun getGridLayoutManager(count: Int): GridLayoutManager {
            return GridLayoutManager(mContext, count);
        }
    }


}