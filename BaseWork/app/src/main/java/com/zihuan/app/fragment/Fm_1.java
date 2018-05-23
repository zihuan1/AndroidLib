package com.zihuan.app.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zihuan.app.R;
import com.zihuan.app.adapter.EmptyAdapter;
import com.zihuan.app.model.UserEntity;
import com.zihuan.app.u.U;
import com.zihuan.app.view.bottom.BaseBottomSheet;
import com.zihuan.app.view.bottom.BottomSheetView;
import com.zihuan.app.xrv.ViewOnItemClick;
import com.zihuan.app.xrv.ViewOnItemLongClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 */
public class Fm_1 extends BaseFragment implements ViewOnItemClick, ViewOnItemLongClick {


    @BindView(R.id.xView)
    XRecyclerView xView;
    EmptyAdapter mAdapter;
    List<UserEntity> mList = new ArrayList<>();
    private static String PERMISSIONS[] = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getLayoutId() {
        return R.layout.item_layout;
    }

    int size;

    @Override
    public void initView(View view) {
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                U.ShowToast("允许");
            } else {
                U.ShowToast("权限被拒绝,请前往设置页面进行设置");
            }
        } else {
            //TODO 相机权限请求 sdk>=23 6.0↑
            PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    U.ShowToast("允许");
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    U.ShowToast("读写被拒绝");
                }
            }, PERMISSIONS);
        }


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
            }
        });
//        Observable.create((ObservableOnSubscribe<String>) emitter -> {
//            UserEntity entity = new UserEntity();
//            entity.setUserName("明明如月");
//            emitter.onNext(mActivity.getDataBase().getUserDao().addUser(entity) + "");
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> Logger.tag("条数 " + integer));
    }

    @Override
    public void setOnItemClickListener(View view, int postion) {
        U.ShowToast(" 点击 " + postion);

        BottomSheetView.build(mActivity)
                .setData("1", "2")
                .setDismissText("关闭")
                .showDialog();
    }

    @Override
    public void setOnItemLongClickListener(View view, int postion) {
        U.ShowToast(" 长按 " + postion);

    }
}
