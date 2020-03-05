package com.zihuan.app.u

import android.text.TextUtils
import com.google.gson.Gson
import java.lang.reflect.Type
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.zihuan.app.Constant
import com.zihuan.app.model.BaseBeanModel


object ZHDataUtils {
    fun isNotNull(str1: String?): Boolean {
        return !TextUtils.isEmpty(str1) && str1 != "null"
    }

    fun modelIsNotNull(model: BaseBeanModel?): Boolean {
        return model != null && model.stateCode == 0
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
            Logger.e("不是DataListModel集合")
//            if (model.data is DataObjModel<*>) {
//                Logger.e("是DataObjModel对象")
//                return entityIsNotNull(model)
//            }
            Logger.e("未知类型")
            return true //如果不是集合的话返回true
        }
        return if (modelIsNotNull(model)) listIsNoNull(model.data as List<*>) else false
    }



    private var gson: Gson? = null
    private var excludeGson: Gson? = null

    fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!
    }

    fun getExcludeGson(): Gson {
        if (excludeGson == null) {
            gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        }
        return gson!!
    }

    fun isJson(json: String): Boolean {
        return json.contains("{") && json.contains("}")
    }

    fun subLast(string: String): String {
//        var lang = guide_service_language?.joinToString(",") {
//            it.language_name
//        }!!
        if (string.isNullOrBlank()) return string
        return string.substring(0, string.length - 1)
    }

    fun toJson(any: Any): String {
        var json = getGson().toJson(any)
        Logger.e(json)
        return json
    }

    fun <T> toEntity(json: String, type: Type): T {
        return getGson().fromJson(json, type)
    }

    fun <T> toListEntity(json: String): ArrayList<T> {
        var type = object : TypeToken<ArrayList<T>>() {}.type
        var a = getGson().fromJson<ArrayList<T>>(json, ArrayList<T>()::class.java)
        return a
    }

    private var exGson: Gson? = null
    fun getExGson(): Gson {
        if (null == exGson)
            exGson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        return exGson!!
    }

}

inline fun getHashMap() = HashMap<String, String>()

