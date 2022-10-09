package com.lyf.live.flowtask

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hjq.toast.ToastUtils
import com.therouter.TheRouter
import com.therouter.TheRouter.addActionInterceptor
import com.therouter.app.flowtask.lifecycle.FlowTask
import com.therouter.flow.TheRouterFlowTask
import com.therouter.router.action.interceptor.ActionInterceptor


/*
    todo 单模块自动初始化能力介绍
    @FlowTask 注解参数说明：
    taskName：当前初始化任务的任务名，必须全局唯一，建议格式为：moduleName_taskName
    dependsOn：参考Gradle Task，任务与任务之间可能会有依赖关系。如果当前任务需要依赖其他任务先初始化，
    则在这里声明依赖的任务名。可以同时依赖多个任务，用英文逗号分隔，空格可选，
    会被过滤：dependsOn = “mmkv, config, login”，默认为空，应用启动就被调用
    async：是否要在异步执行此任务，默认false。
*/
object TestLifecycle {

    //TheRouterFlowTask.APP_ONCREATE：当Application的onCreate()执行后初始化
    //TheRouterFlowTask.APP_ONSPLASH：当应用的首个Activity.onCreate()执行后初始化

    //如果是异步的，执行在APP_ONCREATE 前
    @JvmStatic
    @FlowTask(taskName = "mmkv", dependsOn = TheRouterFlowTask.APP_ONCREATE, async = true)
    fun test1(context: Context?) {
        Log.i("TestLifecycle", "异步=========Application onCreate后执行")
    }

    @JvmStatic
    @FlowTask(taskName = "app1")
    fun test2(context: Context?) {
        Log.i("TestLifecycle", "main线程=========应用启动就会执行")

    }




    @JvmStatic
    @FlowTask(taskName = "test", dependsOn = "mmkv,app1")
    fun test3(context: Context?) {
        Log.i("TestLifecycle", "main线程=========在app1和mmkv两个任务都执行以后才会被执行")
    }

    /**
     * // 假设隐私协议任务名为：AgreePrivacyCache
     * 同意隐私协议后初始化录音SDK
     */
    @JvmStatic
    @FlowTask(taskName = "initRecord", dependsOn = "AgreePrivacyCache")
    fun initRecordAudioSDK(context: Context) {
        Log.i("TestLifecycle", "同意隐私协议后初始化录音SDK")
    }


    //Action 用途
    //
    //Action 本质是一个全局的系统回调，主要用于预埋的一系列操作，例如：弹窗、上传日志、清理缓存。
    // 与 Android 系统自带的广播通知类似，你可以在任何地方声明动作与处理方式。
    // 并且所有Action都是可以被跟踪的，只要你愿意，可以在日志中将所有的动作调用栈输出，以方便调试使用。
    //
    //当用户执行某些操作（打开某个页面、H5点击某个按钮、动态页面配置的点击事件）时，将会自动触发，执行预埋的 Action 逻辑。

    // action建议遵循一定的格式
    const val ACTION1 = "therouter://action/xxx1"
    const val ACTION2 = "therouter://action/xxx2"

    @JvmStatic
    @FlowTask(taskName = "action_demo2")
    fun initActionDemo1(context: Context) {
        TheRouter.addActionInterceptor(ACTION2, object : ActionInterceptor() {
            override fun handle(context: Context, args: Bundle): Boolean {
                // do something
                ToastUtils.show("action_demo2")
                return false
            }
        })
    }


    //FlowTask 是task ,和 action 是2个东西
    //todo 根据同1的action 设置不同的优先级，然后触发触发。。 TheRouter.build(TestLifecycle.ACTION1).action()
    @JvmStatic
    @FlowTask(taskName = "action_demo1", dependsOn = "mmkv,app1")
    fun test33(context: Context?) {
        Log.i("TestLifecycle", "test33 main线程=========在app1和mmkv两个任务都执行以后才会被执行")
        addActionInterceptor(ACTION1, object : ActionInterceptor() {
            override fun handle(context: Context, args: Bundle): Boolean {
                Toast.makeText(context, "业务A弹出，", Toast.LENGTH_SHORT).show()
                return false
            }

            override val priority: Int get() = 6  // 数字越大，优先级越高
        })
        //TODO 根据不同的优化及处理不同的弹框，或者逻辑之类的东西把。
        addActionInterceptor(ACTION1, object : ActionInterceptor() {
            override val priority: Int get() = 7  // 数字越大，优先级越高
            override fun handle(context: Context, args: Bundle): Boolean {
                Toast.makeText(context, "业务B弹出", Toast.LENGTH_SHORT).show()
                return false
            }
        })
    }

}