package com.lyf.scaffold.utils

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.lyf.scaffold.BuildConfig
import java.util.*

object Track {
    // 创建 FirebaseAnalytics 对象
    private val firebaseAnalytics: FirebaseAnalytics by lazy { Firebase.analytics }

    // 做一个监听，可以在上报事件到 firebase 时，也上报到自己服务器
    private var trackListener: TrackListener? = null
    fun setTrackListener(trackListener: TrackListener) {
        Track.trackListener = trackListener
    }

    // 初始化 firebase 信息，只设置了默认参数，默认参数值为设备信息
    fun initTrack(user_id: String = "") {
        val channel = BuildConfig.BUILD_TYPE
        val country = "zh"
        val language = "ch"
        val deviceId = "111"
        val timeZone = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TimeZone.getDefault().id + ""
        } else ""
        val network = "wifi"
        val deviceInfo = "$channel,$country,$language,$deviceId,$timeZone,$network"
        setDefaultEventParameters(Bundle().apply {
            putString(DefaultParam.DEVICE_INFO, deviceInfo)
            if (user_id.isNotEmpty()) {
                putString(Param.USER_ID, user_id)
            }
        })
    }

    fun logEvent(event: String) {
        logEvent(event, null)
    }

    // 最终会调用 logEvent，上报 Firebase 和 自己的监听事件
     fun logEvent(event: String, bundle: Bundle? = null) {
        if (BuildConfig.DEBUG)
            Log.d("TrackFA", "event = $event, bundle = $bundle")
        trackListener?.track(event, bundle)
        firebaseAnalytics.logEvent(event, bundle)
    }

    // 设置用户 id
    fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    // 设置用户属性
    fun setUserProperty(name: String?, value: String?) {
        name ?: return
        if (name.isEmpty()) return
        firebaseAnalytics.setUserProperty(name, value)
    }

    // 设置事件的默认参数
    fun setDefaultEventParameters(bundle: Bundle) {
        firebaseAnalytics.setDefaultEventParameters(bundle)
    }

    // 所有业务事件定义
    object Event {
        const val LOGIN_CLICK = "login_click" // 登陆页点击登录
        const val Game_CLICK = "Game_CLICK" // 点击游戏

    }

    // 所有开发事件定义 由开发者定义，但是要考虑 500 种事件限制！！！
    object DevEvent {
        const val DEV_HTTP_RESP = "dev_http_resp" //http请求结果 异常
    }

    // 所有时间参数的 名称
    object Param {
        const val USER_ID = "user_id"
        const val PHONE = "phone"
    }

    // 所有默认参数的 名称
    object DefaultParam {
        const val DEVICE_INFO = "device_info"
        // Channel,Country,Language,DeviceId,OSVersion,OSModel,TimeZone,Network,CarrierName

    }

    // 所有需要记录用户属性名称
    object UserProperty {
        const val USER_ID = "user_id"
        const val AGE = "age"
        const val BIRTHDAY = "birthday"
        const val COUNTRY_NAME = "country_name"
        const val GENDER = "gender"
    }

    interface TrackListener {
        fun track(event: String, bundle: Bundle?)
    }
}