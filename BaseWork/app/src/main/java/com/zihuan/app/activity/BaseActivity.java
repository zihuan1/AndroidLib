package com.zihuan.app.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okhttputils.OkHttpUtils;
import com.zihuan.app.R;
import com.zihuan.app.UserManager;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.task.OkHttpListener;
import com.zihuan.app.u.DateUtil;
import com.zihuan.app.u.Logger;
import com.zihuan.app.u.U;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 */
public class BaseActivity extends FragmentActivity {
    public String uid;
    public String token = "";
    public String role = "";
    public int page = 1;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!U.CheckNetworkConnected()) {
            U.ShowToast("请检测网络后再试");
        }
        hideKeyboard();
        initSystemBar(this, R.color.colorPrimary);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.developerArg0 = "Basic builder 1";
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);*/

        //设置 actionSheet 样式 ios7
//        setTheme(R.style.ActionSheetStyleiOS7);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uid = UserManager.getInstance().userData.getUid();
        role = UserManager.getInstance().userData.getRole();
        if (!isNull2(UserManager.getInstance().userData.getToken())) {
            token = UserManager.getInstance().userData.getToken();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
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

    //发短信
    private void sendSMS() {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
//        sendIntent.putExtra("sms_body", mEditText.getText().toString());
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }

    /***
     * 设置系统状态栏的颜色
     *
     * @param activity
     * @param colorId
     */
    public static void initSystemBar(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintResource(colorId);
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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

    private String tel;

    //拨号功能
    public void tel(final String tel) {
        this.tel = tel.replace(" ", "");
//        new CallDialog(this, 1).getMyDialog("拨打电话", "是否拨号？");
    }

    //    判断电话号码格式
    public static boolean isMobileNO(String mobiles) {
        if (!TextUtils.isEmpty(mobiles)) {

            String expression = "^((0\\d{2,3}-\\d{7,8})|(0\\d{2,3}-\\d{11})|(1[35784]\\d{9}))$";

            Pattern p = Pattern.compile(expression);//((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            U.ShowToast("手机号不能为空");
            return false;
        }
    }


//    @Override
//    public void isY(boolean flg, int wht, int pos) {
//        if (flg) {
//            if (!isMobileNO(tel)) {
//                U.ShowToast("格式不正确");
//                Log.e("----", "tel=" + tel);
//            } else {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//                        + tel));
//                startActivity(intent);
//            }
//        }
//    }


    public boolean isNetWork() {
        if (U.CheckNetworkConnected()) {
            return true;
        }
        U.ShowToast("网络异常");
        return false;
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

    /***
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //    是否是自己
    public boolean isMe(String u) {
        if (uid == u) {
            return true;
        }
        return false;
    }


}
