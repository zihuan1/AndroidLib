package com.zihuan.app.task;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface Api {

    /***
     *
     * @param url 请求地址
     * @param map 请求参数
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> listRepos(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> login(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> reg(@Url String url, @FieldMap Map<String, String> map);


}
