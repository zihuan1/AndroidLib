package com.zihuan.app.fragment;

import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zihuan.app.R;
import com.zihuan.app.adapter.EmptyAdapter;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.U;
import com.zihuan.app.xrv.ViewOnItemClick;
import com.zihuan.app.xrv.ViewOnItemLongClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 */
public class Fm_1 extends BaseFragment implements ViewOnItemClick, ViewOnItemLongClick {


    @Bind(R.id.xView)
    XRecyclerView xView;
    EmptyAdapter mAdapter;
    List<UserEntity> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.item_layout;
    }

    int size;

    @Override
    public void initView(View view) {
        mActivity.iniXrecyclerView(xView);
        mAdapter = new EmptyAdapter(mList, this);
//        xView.setEmptyView();
        xView.setAdapter(mAdapter);
        xView.setLoadingMoreEnabled(true);
        for (size = 1; size < 15; size++) {
            UserEntity entity = new UserEntity();
            entity.setUserName("小明" + size);
            mList.add(entity);
        }
        mAdapter.updata(mList);
        xView.setNoMore(false);
        xView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                for (size = 1; size < 15; size++) {
                    UserEntity entity = new UserEntity();
                    entity.setUserName("小明" + size);
                    mList.add(entity);
                }
                mAdapter.updata(mList);
                xView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                for (; size <= 20; size++) {
                    UserEntity entity = new UserEntity();
                    entity.setUserName("小明" + size);
                    mList.add(entity);
                }
                mAdapter.updata(mList);
                xView.loadMoreComplete();
//                xView.setNoMore(true);
            }
        });
    }

    @Override
    public void setOnItemClickListener(View view, int postion) {
        U.ShowToast(" 点击 " + postion);
    }

    @Override
    public void setOnItemLongClickListener(View view, int postion) {
        U.ShowToast(" 长按 " + postion);

    }
}
