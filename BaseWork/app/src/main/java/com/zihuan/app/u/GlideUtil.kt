package com.zihuan.app.u

import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.zihuan.app.MainApplication
import com.zihuan.app.R


/**
 *
 */
object GlideUtil {


    //    加载头像
    //    public static void getGlideHeadic_launcher(String url, ImageView view) {
    //        Glide.with(MainApplication.mainApplication)
    //                .load(url)
    ////                .asGif()//显示gif动画,asGif()判断是否是gif动画
    //                .placeholder(R.mipmap.ic_launcher)//加载中显示的图片
    //                .error(R.mipmap.ic_launcher)//加载失败时显示的图片
    //                .crossFade()//渐入
    ////                .centerCrop()//图片显示方式
    ////                .override(600, 200)//设置大小
    ////                .priority(Priority.HIGH)//优先级
    ////                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
    //                .into(view);
    ////缓存策略
    ////        DiskCacheStrategy.NONE 什么都不缓存
    ////        DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
    ////        DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
    ////        DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
    //
    ////        1图片加载优先级
    ////        Priority.LOW
    ////        Priority.NORMAL
    ////        Priority.HIGH
    ////        Priority.IMMEDIATE
    //    }

    //    加载头像
    fun getGlideHead(url: String, view: ImageView) {
        Glide.with(MainApplication.mainApplication)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)//加载中显示的图片
                .error(R.mipmap.ic_launcher)//加载失败时显示的图片
                //                .crossFade()//渐入
                .into(view)
    }

    //    加载背景
    fun getBackGround(url: String, view: ImageView) {
        if (TextUtils.isEmpty(url)) {
            view.setImageDrawable(MainApplication.mainApplication.resources.getDrawable(R.mipmap.ic_launcher))
            return
        }
        Glide.with(MainApplication.mainApplication)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)//加载中显示的图片
                .error(R.mipmap.ic_launcher)//加载失败时显示的图片
                //                .crossFade()//渐入
                .into(view)
    }


    fun getBitmap(url: String, view: ImageView) {
        //  获取Bitmap
        Glide.with(MainApplication.getInstance())
                .load(url)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(bitmap: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                        view.setImageBitmap(bitmap)
                    }
                })
    }

    var simpleTarget: SimpleTarget<*> = object : SimpleTarget<Bitmap>() {
        override fun onResourceReady(bitmap: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {

        }
    }
}



