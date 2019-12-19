package com.zihuan.app.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zihuan.baseadapter.RecyclerViewHolder

@SuppressLint("StaticFieldLeak")
object RecycleLayout {
   private var mContext: Context? = null

    //横向布局
    fun androidx.recyclerview.widget.RecyclerView.initHorizontal() {
        mContext = this.context
        this.layoutManager = RecycleLayout.RecycleViewBuilder.getLinLayoutManager(androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, this)
    }

    fun androidx.recyclerview.widget.RecyclerView.initHorizontal(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewHolder>) {
        initHorizontal()
        this.adapter = adapter
    }


    //竖向布局
    fun androidx.recyclerview.widget.RecyclerView.initVertical(): androidx.recyclerview.widget.LinearLayoutManager {
        mContext = this.context
        this.layoutManager = RecycleLayout.RecycleViewBuilder.getLinLayoutManager(androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, this)
        return this.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
    }

    fun androidx.recyclerview.widget.RecyclerView.initVertical(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewHolder>) {
        initVertical()
        this.adapter = adapter
    }


    fun androidx.recyclerview.widget.RecyclerView.initGrid(count: Int): androidx.recyclerview.widget.GridLayoutManager {
        mContext = this.context
        this.layoutManager = RecycleLayout.RecycleViewBuilder.getGridLayoutManager(count, this)
        return this.layoutManager as androidx.recyclerview.widget.GridLayoutManager
    }

    fun androidx.recyclerview.widget.RecyclerView.initGrid(count: Int, adapter: androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewHolder>) {
        initGrid(count)
        this.adapter = adapter
    }


    private object RecycleViewBuilder {
        /***
         * orientation 0横向 1竖向
         */
        fun getLinLayoutManager(orientation: Int, view: androidx.recyclerview.widget.RecyclerView): androidx.recyclerview.widget.LinearLayoutManager {
            val layoutManager = object : androidx.recyclerview.widget.LinearLayoutManager(mContext) {
                override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler?, state: androidx.recyclerview.widget.RecyclerView.State?) {
                    super.onLayoutChildren(recycler, state)
                }
            }
            view.isNestedScrollingEnabled = false
            layoutManager.orientation = orientation
            return layoutManager
        }

        fun getGridLayoutManager(count: Int, view: androidx.recyclerview.widget.RecyclerView): androidx.recyclerview.widget.GridLayoutManager {
            view.isNestedScrollingEnabled = false
            return androidx.recyclerview.widget.GridLayoutManager(mContext, count)
        }
    }


}