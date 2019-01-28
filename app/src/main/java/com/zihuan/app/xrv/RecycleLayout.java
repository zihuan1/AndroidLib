package com.zihuan.app.xrv;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zihuan.app.R;


public class RecycleLayout {
    public static Context mContext;
    static RecycleLayout viewLayout;

    public static RecycleLayout builder(Context context) {
        if (viewLayout == null) viewLayout = new RecycleLayout();
        mContext = context;
        return viewLayout;
    }

    //    初始化xrv的配置
    public void initXrecyclerView(XRecyclerView xRecyclerView) {
        xRecyclerView.setLayoutManager(RecycleViewBuilder.initRecycleLayout().getLinLayoutManager(LinearLayoutManager.VERTICAL));
        initXrvConfig(xRecyclerView);
    }


    //    横向布局
    public void initHorizontal(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(RecycleViewBuilder.initRecycleLayout().getLinLayoutManager(LinearLayoutManager.HORIZONTAL));
    }

    //    竖向布局
    public void initVertical(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(RecycleViewBuilder.initRecycleLayout().getLinLayoutManager(LinearLayoutManager.VERTICAL));
    }

    //     可刷新的配置
    public void initXGrid(XRecyclerView xRecyclerView, int count) {
        xRecyclerView.setLayoutManager(RecycleViewBuilder.initRecycleLayout().getGridLayoutManager(count));
        initXrvConfig(xRecyclerView);
    }

    //    九宫格布局
    public void initGrid(RecyclerView recyclerView, int count) {
        recyclerView.setLayoutManager(RecycleViewBuilder.initRecycleLayout().getGridLayoutManager(count));
    }

    // xrv配置
    private void initXrvConfig(XRecyclerView xRecyclerView) {
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        xRecyclerView.setLoadingMoreEnabled(false);
    }

    private static class RecycleViewBuilder {
        static RecycleViewBuilder viewBuilder;

        public static RecycleViewBuilder initRecycleLayout() {
            if (viewBuilder == null) viewBuilder = new RecycleViewBuilder();
            return viewBuilder;
        }

        public LinearLayoutManager getLinLayoutManager(int orientation) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(orientation);
            return layoutManager;
        }

        //九宫格布局
        public GridLayoutManager getGridLayoutManager(int count) {
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, count);
            return layoutManager;
        }
    }
}
