package com.zihuan.app.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okhttputils.OkHttpUtils;
import com.zihuan.app.Constant;
import com.zihuan.app.R;
import com.zihuan.app.UserManager;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.roomdata.UserDataBase;
import com.zihuan.app.task.Api;
import com.zihuan.app.task.OkHttpListener;
import com.zihuan.app.u.DateUtil;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.U;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 */
public abstract class BaseActivity extends FragmentActivity {
    public String uid;
    public String token = "";

    UserDataBase mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!U.CheckNetworkConnected()) {
            U.ShowToast("请检测网络后再试");
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        initView();
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        uid = UserManager.getInstance().userData.getUid();
        if (!isNull2(UserManager.getInstance().userData.getUid())) {
            uid = UserManager.getInstance().userData.getUid();
            token = UserManager.getInstance().userData.getToken();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    //  获取系统时间
    public String getSysTime(String type) {
        if (isNull2(type)) {
            type = "yyyy/MM/dd/HH:mm";
        }
        return DateUtil.getSysDate(type);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    初始化 RecyclerView的配置
    public void iniXrecyclerView(XRecyclerView xRecyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        xRecyclerView.setLoadingMoreEnabled(false);
    }
    //    横向布局
    public void iniXrecyclerViewH(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
    }

    //    竖向布局
    public void iniXrecyclerViewV(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
    }

    //    空置点击事件
    public void emptyClick(View view) {
    }

    /**
     * @param url
     * @param map
     * @param obj     数据模型
     * @param handler 数据回调的handler
     * @param what    hander标记值
     */
    public void getOkHttpJsonRequest(String url, Map<String, String> map, Object obj, Handler handler, int what) {
        if (map != null) {
            OkHttpUtils.post(url)
                    .params(map)
//                    .cacheKey("myFriend")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                    .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                    .execute(new OkHttpListener(handler, what, obj));
            Logger.tag("请求参数 " + map.toString());
        } else {
            OkHttpUtils.post(url)
                    .execute(new OkHttpListener(handler, what, obj));
        }
    }


    public boolean modelIsNull(BaseBeanModel model) {

        if (model == null || model.getStateCode() != 0) {
            Logger.tag("---model---为空或code不等于0" + model.toString());
            return true;
        }
        return false;
    }

    public boolean listIsNull(List list) {
        if (list == null || list.size() == 0) {
            Logger.tag("---list---为空或size等于0" + list);
            return true;
        }
        return false;
    }

    //    数据是否为空
    public boolean dataIsNull(BaseBeanModel model, List list) {
        return modelIsNull(model) == true ? true : listIsNull(list);
    }


    /**
     * @param str1 需要判断的输入框数据
     * @param str  为空时返回的数据
     * @return
     */
    public boolean isNull(String str1, String str) {
        if (TextUtils.isEmpty(str1)) {
            U.ShowToast("请您输入" + str + "");
            return true;
        }
        return false;
    }

    /**
     * @param str1 需要判断的输入框数据
     * @return
     */
    public boolean isNull2(String str1) {
        if (TextUtils.isEmpty(str1) || str1.equals("null")) {
//            U.ShowToast(str + "不能为空");
            return true;
        }
        return false;
    }

    //不随系统字体改变大小
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        try {
            res.updateConfiguration(config, res.getDisplayMetrics());
        } catch (Exception e) {
        }
        return res;
    }

    public void back(View view) {
        finish();
    }

    //    是否登陆
    public boolean isLogin() {

        if (isNull2(uid)) {
            Logger.tag("-------没有登录-------");
            return false;
        }
        Logger.tag("--------登录了------" + uid);
        return true;
    }

    // 获取布局文件
    public abstract int getLayoutId();

    // 初始化view
    public abstract void initView();

    // 数据加载
    public abstract void initData();

//    LoadingDialog dialog;
//
//    public void showDialog() {
//        dialog = new LoadingDialog(this);
//        dialog.setLoadingText("加载中...");//设置loading时显示的文字
//        dialog.setInterceptBack(false);
//        dialog.show();
//    }
//
//    public void dismissDialog() {
//        if (dialog == null) return;
//        dialog.close();
//        dialog = null;
//    }

    public UserDataBase getDataBase() {
        return mDataBase == null ? mDataBase = UserDataBase.getDatabase(this) : mDataBase;
    }

    private static Retrofit RetrofitApi() {

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
//                .client(builder.build())//添加okhttp设置
//                .addConverterFactory(GsonConverterFactory.create())//gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx适配器
                .build();
    }

    public void getRetiofitJsonRequest(Observer observer, String url, Map map) {
        Api service = RetrofitApi().create(Api.class);
        service.listRepos(url, map)
                .subscribeOn(Schedulers.io())//在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//回到主线程去处理请求结果
                .subscribe(observer);
    }
}
