package com.lyf.scaffold.adn

import android.content.Context
import android.util.Log
import com.bytedance.sdk.openadsdk.*

/**
 * 可以用一个单例来保存TTAdManager实例，在需要初始化sdk的时候调用
 */
class TTAdManagerHolder private constructor() {

    companion object {
        private val instance: TTAdManagerHolder by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TTAdManagerHolder()
        }

        @JvmStatic
        fun get(): TTAdManagerHolder {
            return instance
        }

        @JvmStatic
        fun getTTAdManager(): TTAdManager? {
            if (!TTAdSdk.isInitSuccess()) {
                return null
            }
            return TTAdSdk.getAdManager()
        }
    }

    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    //穿山甲SDK初始化
    //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
    fun doInit(context: Context) {
        ///经测试发现TTAdSdk.isInitSuccess()参数无效  初始化成功之后还是false?
        Log.i("穿山甲SDK初始化结果", TTAdSdk.isInitSuccess().toString());
        TTAdSdk.init(context, buildConfig(context, "5340931"), object : TTAdSdk.InitCallback {
            override fun success() {
                Log.i(
                    "穿山甲SDK初始化成功", TTAdSdk.isInitSuccess().toString() + "---"
                            + TTAdSdk.getAdManager().sdkVersion
                );

            }

            override fun fail(p0: Int, p1: String?) {
                Log.i("穿山甲SDK初始化失败", "$p0-------$p1");
            }
        })
    }

    private fun buildConfig(context: Context, appId: String): TTAdConfig {
        return TTAdConfig.Builder()
            .appId(appId)
            .appName("蛋蛋模拟器")
            .paid(true)
            ///添加这个控制之后可能造成模拟器闪退/不能下载文件
            .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
            .allowShowNotify(true) //是否允许sdk展示通知栏提示
            //.allowShowPageWhenScreenLock(true) // 锁屏下穿山甲SDK不会再出落地页，此API已废弃，调用没有任何效果
            .debug(BuildConfig.DEBUG) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
            .directDownloadNetworkType(
                TTAdConstant.NETWORK_STATE_WIFI,
                TTAdConstant.NETWORK_STATE_3G,
                TTAdConstant.NETWORK_STATE_4G
            ) //允许直接下载的网络状态集合
            .supportMultiProcess(false) //是否支持多进程
            .needClearTaskReset()
            .build()
    }
}
