package com.zihuan.app.task;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zihuan.app.Constant;
import com.zihuan.app.model.UserModel;
import com.zihuan.app.u.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edz on 2018/3/20.
 */

public class ApiServer {
    public static Api movieService;


    public static Api getDefaultService() {
        if (movieService == null) {
            movieService = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
//                .client(builder.build())//添加okhttp设置
                    .addConverterFactory(GsonConverterFactory.create())//gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx适配器
                    .build().create(Api.class);
        }
        return movieService;
    }

//    示例
//       ApiServer.getDefaultService().listRepos(Constant.GET_SITELIST, map)
//                .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Consumer<ResponseBody>() {
//        @Override
//        public void accept(ResponseBody fieldModel) throws Exception {
//            String json = fieldModel.string();
//            UserModel model = new Gson().fromJson(json, UserModel.class);
//        }
//    });
}
