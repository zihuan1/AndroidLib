package com.zihuan.app.ui.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zihuan.app.R
import com.zihuan.app.base.RecycleLayout.initGrid
import com.zihuan.app.base.RecycleLayout.initHorizontal
import com.zihuan.app.base.RecycleLayout.initVertical
import com.zihuan.baseadapter.RecycleViewDivider
import com.zihuan.baseadapter.SuperRecycleAdapter
import kotlinx.android.synthetic.main.recycle_layout.view.*

/***
 * recycleview 模版
 */
class RecycleAssViewLayout : FrameLayout {

    constructor(context: Context) : super(context) {
        createView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        createView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        createView()
    }


    lateinit var mActivity: Activity
    private fun createView() {
        var view = View.inflate(context, R.layout.recycle_layout, null)
        addView(view)
        mActivity = context as Activity
    }

    fun buildVerticalLayout(adapter: SuperRecycleAdapter): Builder {
        return Builder(re_view, mActivity, sr_layout).defBuild(adapter, LinearLayoutManager.VERTICAL)
    }

    fun buildHorizontalLayout(adapter: SuperRecycleAdapter): Builder {
        return Builder(re_view, mActivity, sr_layout).defBuild(adapter, LinearLayoutManager.HORIZONTAL)
    }

    fun buildGridLayout(adapter: SuperRecycleAdapter, type: Int): Builder {
        return Builder(re_view, mActivity, sr_layout).defBuild(adapter, type)
    }


    class Builder {
        private var mContext: Context
        private var re_view: RecyclerView
        private var sr_layout: SmartRefreshLayout

        constructor(re_view1: RecyclerView, context: Context, sr_layout1: SmartRefreshLayout) {
            re_view = re_view1
            sr_layout = sr_layout1
            mContext = context
        }

        //    https://www.jianshu.com/p/3f30fb2c4e47
        private var scrollToBottom = false
        private lateinit var rAdapter: SuperRecycleAdapter
        internal fun defBuild(adapter: SuperRecycleAdapter, type: Int): Builder {
            rAdapter = adapter
            when (type) {
                LinearLayoutManager.VERTICAL -> re_view.initVertical(rAdapter)
                LinearLayoutManager.HORIZONTAL -> re_view.initHorizontal(rAdapter)
                else -> re_view.initGrid(type, rAdapter)
            }
            return this
        }

        fun setBackGround(bg: Drawable): Builder {
            re_view.background = bg
            return this
        }

        //设置刷新
        fun setPullEnabled(boolean: Boolean = true): Builder {
            sr_layout.setEnableRefresh(boolean)
            return this
        }

        //    设置加载
        fun setLoadEnabled(boolean: Boolean = true): Builder {
            sr_layout.setEnableLoadMore(boolean)
            return this
        }

        fun setOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener): Builder {
            setPullEnabled(true)
            setLoadEnabled(true)
            sr_layout.setOnRefreshLoadMoreListener(listener)
            return this
        }

        //加载或者是刷新完成
        fun loadOrPullComplete() {
            sr_layout.finishRefresh()
            sr_layout.finishLoadMore()
        }

        fun setData(arr: List<*>?): Builder {
            rAdapter.update(arr)
            loadOrPullComplete()
            if (scrollToBottom) {
//            如果需要关闭自动滚动，要手动在调用一次scrollToBottom()
                scrollToPosition(rAdapter.itemCount)
            }
            return this
        }

        /***
         * draw  色值
         * isDrawBottom 是否画最后一条分割线
         */
        fun setDivider(draw: Int, isDrawBottom: Boolean = true): Builder {
            var rvd = RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, draw)
            rvd.setDrawBotton(isDrawBottom)
            getRView().addItemDecoration(rvd)
            return this
        }

        fun setDivider(rvd: RecyclerView.ItemDecoration): Builder {
            getRView().addItemDecoration(rvd)
            return this
        }

        fun scrollToBottom(scroll: Boolean = true): Builder {
            scrollToBottom = scroll
            return this
        }

        fun scrollToPosition(pos: Int): Builder {
            re_view.smoothScrollToPosition(pos)
            return this
        }

        //以后设置一个默认的
        fun setEmptyView(emptyView: View) {
            if (rAdapter.baseDatas.size == 0) {
                emptyView.visibility = View.VISIBLE
            } else {
                emptyView.visibility = View.GONE
            }
        }

        //获取当前rv
        fun getRView(): RecyclerView {
            return re_view
        }

        //滚动到指定位置
        fun setCurrentPosition(pos: Int) {
            getRView().scrollToPosition(pos)
        }
    }

}