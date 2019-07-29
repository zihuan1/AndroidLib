package com.zihuan.app.adapter

import android.content.Context
import com.zihuan.app.R
import com.zihuan.app.model.UserEntity
import com.zihuan.baseadapter.RecyclerAdapter
import com.zihuan.baseadapter.RecyclerViewHolder

class DemoAdapter(`object`: Any?) : RecyclerAdapter(`object`) {
    override fun convert(holder: RecyclerViewHolder, position: Int, context: Context) {
        var entity = getEntity<UserEntity>(position)
        var tv_name = holder.getTextView(R.id.tv_name)
//        var iv_choose = holder.getImageView(R.id.iv_choose)
//        var v_city_lin = holder.getView(R.id.v_city_lin)
//        tv_city_name.text = entity.city_name
//        v_city_lin.run {
//        }
        tv_name.text=entity.userName
    }

    override fun getLayoutResId(): Int {
        return R.layout.rv_layout
    }
}