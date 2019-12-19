package com.zihuan.app.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import com.lzy.okhttputils.OkHttpUtils;
import com.tripsdiy.app.u.DataUtils;
import com.tripsdiy.app.u.Logger;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zihuan.app.Constant;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.task.HttpCallBack;
import com.zihuan.app.task.OkHttpListener;
import com.zihuan.app.task.RequestCallBack;
import com.zihuan.app.u.U;
import com.zihuan.rsautils.RSAUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean isNoNull(String str1) {
        return DataUtils.INSTANCE.isNoNull(str1);
    }


    public boolean isNullToast(String str1, String toast) {
        if (!DataUtils.INSTANCE.isNoNull(str1)) {
            U.ShowToast(toast);
            return true;
        } else {
            return false;
        }
    }

    public boolean modelIsNotNull(BaseBeanModel model) {
        return DataUtils.INSTANCE.modelIsNotNull(model);
    }

    public boolean listIsNoNull(List list) {
        return DataUtils.INSTANCE.listIsNoNull(list);
    }

    //    数据是否为空
    public boolean dataIsNotNull(BaseBeanModel model) {
        return DataUtils.INSTANCE.dataIsNotNull(model);
    }

    public boolean entityIsNotNull(BaseBeanModel model) {
        return DataUtils.INSTANCE.entityIsNotNull(model);
    }

    public void back(View view) {
        finish();
    }


    //    空置点击事件
    public void emptyClick(View view) {

    }


    public LoadingDialog dialog;
    public boolean isShow;

    public void showDialog() {
        if (!isFinishing() && !isShow) {
            dialog = new LoadingDialog(this);
            dialog.setLoadingText("加载中...");//设置loading时显示的文字
            dialog.setInterceptBack(false);
            dialog.show();
            isShow = true;
        }
    }

    public void dismissDialog() {
        if (dialog == null || isFinishing()) return;
        dialog.close();
        dialog = null;
        isShow = false;
    }


    public void startSuperActivity(Class<?> cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    public void startSuperActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }


    public void getOkHttpJsonRequest(String url, Map<String, String> encodeMap, RequestCallBack callBack) {
        Type type = getBaseViewType(callBack);
        Logger.INSTANCE.tag("加密前的参数：" + encodeMap.toString());
        if (!encodeMap.containsKey(Constant.INSTANCE.getNO_RSA())) {
//            如果没有登录 跳出方法
            if (encodeMap.containsKey("user_id") && TextUtils.isEmpty(encodeMap.get("user_id"))) {
                encodeMap.remove("user_id");
                encodeMap.remove("user_token");
            }
            encodeMap.put("version", "1");
//            encodeMap.put("deviceId", Build.SERIAL);
            encodeMap.put("deviceType", "1");//1安卓 2ios
            encodeMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
            encodeMap.put("appKey", U.MD5(Constant.INSTANCE.getAPI_KEY()));
//           Logger.INSTANCE.tag( "参数----" + encodeMap.toString());
            try {
                // 从字符串中得到公钥
                RSAUtils.loadPublicKey(Constant.INSTANCE.getPUBLIC_KEY());
                // 加密
                String encryptByte = RSAUtils.encryptWithRSA(U.transMap2String(encodeMap));
                HashMap<String, String> params = new HashMap<>();
                params.put("params", encryptByte);  // 加密的参数串
                OkHttpUtils.post(url)
                        .params(params)
                        .execute(new OkHttpListener(callBack, type));
            } catch (Exception e) {
                Logger.INSTANCE.tag("加密异常 " + e.getMessage());
            }
        } else {
            OkHttpUtils.post(url)
                    .params(encodeMap)
                    .execute(new OkHttpListener(callBack, type));
        }
    }

    public Type getBaseViewType(RequestCallBack callBack) {
        Type type;
        try {
            Type[] types = null;
            ParameterizedType parameterized = null;
            if (callBack instanceof HttpCallBack) {
                parameterized = (ParameterizedType) callBack.getClass().getGenericSuperclass();
            } else if (callBack instanceof RequestCallBack) {
                types = callBack.getClass().getGenericInterfaces();
                parameterized = (ParameterizedType) types[0];
            }
//            获取当前类的接口泛型
            type = parameterized.getActualTypeArguments()[0];
        } catch (Exception e) {
            Logger.INSTANCE.tag("泛型异常 " + e.toString());
            return null;
        }
        return type;
    }
}
