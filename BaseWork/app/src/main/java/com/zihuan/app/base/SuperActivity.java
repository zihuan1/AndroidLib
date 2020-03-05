package com.zihuan.app.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.lzy.okhttputils.OkHttpUtils;
import com.orhanobut.logger.Logger;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zihuan.app.Constant;
import com.zihuan.app.model.BaseBeanModel;
import com.zihuan.app.task.HttpCallBack;
import com.zihuan.app.task.OkHttpListener;
import com.zihuan.app.task.RequestCallBack;
import com.zihuan.app.u.ZHDataUtils;
import com.zihuan.rsautils.EncryptionUtils;
import com.zihuan.rsautils.RSAUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.zihuan.utils.cmhlibrary.CommonHeplerKt.ShowToast;

public class SuperActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    public void getOkHttpJsonRequest(String url, Map<String, String> encodeMap, RequestCallBack callBack) {
        Type type = getBaseViewType(callBack);
        Logger.e("加密前的参数：" + encodeMap.toString());
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
            encodeMap.put("appKey", EncryptionUtils.MD5(Constant.INSTANCE.getAPI_KEY()));
//           Logger.e( "参数----" + encodeMap.toString());
            try {
                // 从字符串中得到公钥
                RSAUtils.loadPublicKey(Constant.INSTANCE.getPUBLIC_KEY());
                // 加密
                String encryptByte = RSAUtils.encryptWithRSA(transMap2String(encodeMap));
                HashMap<String, String> params = new HashMap<>();
                params.put("params", encryptByte);  // 加密的参数串
                OkHttpUtils.post(url)
                        .params(params)
                        .execute(new OkHttpListener(callBack, type));
            } catch (Exception e) {
                Logger.e("加密异常 " + e.getMessage());
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
            Logger.e("泛型异常 " + e.toString());
            return null;
        }
        return type;
    }


    public boolean isNoNull(String str1) {
        return ZHDataUtils.INSTANCE.isNotNull(str1);
    }


    public boolean isNullToast(String str1, String toast) {
        if (!ZHDataUtils.INSTANCE.isNotNull(str1)) {
            ShowToast(toast);
            return true;
        } else {
            return false;
        }
    }


    public boolean listIsNoNull(List list) {
        return ZHDataUtils.INSTANCE.listIsNoNull(list);
    }

    //    数据是否为空
    public boolean dataIsNotNull(BaseBeanModel model) {
        return ZHDataUtils.INSTANCE.dataIsNotNull(model);
    }

    /**
     * map转为字符串
     *
     * @param map
     * @return
     */
    public static String transMap2String(Map map) {
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }

}
