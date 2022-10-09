package com.lyf.live.converter

import com.drake.net.convert.JSONConvert
import com.google.gson.GsonBuilder
import com.hjq.gson.factory.GsonFactory
import org.json.JSONObject
import java.lang.reflect.Type


class GsonConverter : JSONConvert(code = "errorCode", message = "errorMsg") {
    companion object {
//        private val gson = GsonBuilder().serializeNulls().create()
        // 获取单例的 Gson 对象（已处理容错）
        var gson = GsonFactory.getSingletonGson()
    }

    override fun <R> String.parseBody(succeed: Type): R? {
        return try {
            gson.fromJson(JSONObject(this).getString("data"), succeed)
        } catch (e: Exception) {
            gson.fromJson(this, succeed)
        }
    }
}