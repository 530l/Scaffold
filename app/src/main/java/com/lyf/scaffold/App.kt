package com.lyf.scaffold

import android.app.Application
import android.util.Log
import com.lyf.export_data.model1.Model1RoutePath
import com.lyf.live.Model1RvActivity
import com.therouter.router.RouteItem
import com.therouter.router.interceptor.InterceptorCallback
import com.therouter.router.interceptor.RouterInterceptor
import com.therouter.router.setRouterInterceptor


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("TestLifecycle", "App main线程=========应用启动就会执行")


        //3.3 路由替换器
        //应用场景：常用在未登录不能使用的页面上。例如访问用户钱包页面，在钱包页声明的时候，
        // 可以在路由表上声明本页面是需要登录的，在路由跳转过程中，如果落地页是需要登录的，则先替换路由到登录页，
        // 同时将原落地页信息作为参数传给登录页，登录流程处理完成后可以继续执行之前的路由操作。
        //路由替换器的拦截点更靠后，主要用于框架已经从路由表中根据 path 找到路由以后，对找到的路由做操作。
        //这种逻辑在所有页面跳转前写不太合适，以前的做法通常是在落地页写逻辑判断用户是否具有权限，但其实在路由层完成更合适。
        //注：必须在 TheRouter.build().navigation() 方法调用前添加处理器，否则处理器前的所有跳转不会被替换。
        /*     addRouterReplaceInterceptor(object : RouterReplaceInterceptor() {
                 override fun replace(routeItem: RouteItem?): RouteItem? {
                     if (routeItem?.path == "/Model1Activity") {
                         Log.i("Interceptor", "Model1Activity 路由替换器")
                         val target = RouteItem()
                         target.className = Model1RvActivity::class.java.name
                         target.path = Model1RoutePath.Model1RvActivityPATH
                         target.description = "也可以在这里修改原有路由的参数信息"
                         return target
                     }
                     return routeItem
                 }
             })*/


        //3.4 路由AOP拦截器
        //与前三个处理器不同的点在于，路由的AOP拦截器全局只能有一个。
        // 用于实现AOP的能力，在整个TheRouter跳转的过程中，跳转前，
        // 目标页是否找到的回调，跳转时，跳转后，都可以做一些自定义的逻辑处理。
        //使用场景：场景很多，最常用的是可以拦截一些跳转，例如debug页面在生产环境不打开，或定制startActivity跳转方法。
        setRouterInterceptor(object : RouterInterceptor {
            override fun process(routeItem: RouteItem, callback: InterceptorCallback) {
                if (routeItem.path == "/Model1Activity/xxxx") {
                    Log.i("Interceptor", "Model1Activity 路由AOP拦截器")
//                    val target = RouteItem()
//                    target.className = Model1RvActivity::class.java.name
//                    target.path = Model1RoutePath.Model1RvActivityPATH
//                    routeItem.getExtras().getString("key2")?.let { target.addParams("key2", it) }
//                    target.description = "也可以在这里修改原有路由的参数信息"
//                    callback.onContinue(target);
                    return
                } else {
                    // callback 对象也是可以自定义的，看你怎么用了
                    callback.onContinue(routeItem);
                }
            }
        })
    }
}