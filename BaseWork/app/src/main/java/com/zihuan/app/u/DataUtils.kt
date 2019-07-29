package com.tripsdiy.app.u

import android.text.TextUtils
import com.google.gson.Gson
import com.zihuan.app.Constant
import com.zihuan.app.model.BaseBeanModel

object DataUtils {
    fun isNoNull(str1: String?): Boolean {
        return !TextUtils.isEmpty(str1) && str1 != "null"
    }

    fun modelIsNotNull(model: BaseBeanModel?): Boolean {
        return !(model == null || model.stateCode != 0)
    }

    fun listIsNoNull(list: List<*>): Boolean {
        return list != null && list.isNotEmpty()
    }

    fun entityIsNotNull(model: BaseBeanModel): Boolean {
        return if (modelIsNotNull(model)) model.data != null else false
    }

    //    数据是否为空
    fun dataIsNotNull(model: BaseBeanModel): Boolean {
        if (model.data == null) return false
        if (model.data !is List<*>) {
            Logger.tag("不是DataListModel集合")
//            if (model.data is DataObjModel<*>) {
//                Logger.tag("是DataObjModel对象")
//                return entityIsNotNull(model)
//            }
            Logger.tag("未知类型")
            return true //如果不是集合的话返回true
        }
        return if (modelIsNotNull(model)) listIsNoNull(model.data as List<*>) else false
    }



    private var gson: Gson? = null

    fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!
    }

    fun isGson(json: String): Boolean {
        return json.contains("{") && json.contains("}")
    }

    fun subLast(string: String): String {
        if (string.isNullOrBlank()) return string
        return string.substring(0, string.length - 1)
    }

//    //    type = 1（所有空格 ）、 2（前后空格）、 3（前空格）、 4（后空格）
//    fun trim(str: String, type: Int=1) {
//        when (type) {
//            1 -> str.replace("/\\s+/g", "")
//            2 -> str.replace("/(^\\s*)|(\\s*\$)/g", "")
//            3 -> str.replace("/(^\\s*)/g", "")
//            4 -> str.replace("/(\\s*\$)/g", "")
//            else -> str
//        }
//    }
}