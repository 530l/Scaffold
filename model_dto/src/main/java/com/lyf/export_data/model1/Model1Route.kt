package com.lyf.export_data.model1

import android.content.Context
import com.therouter.TheRouter


object Model1Route {


    fun navigationModel1Activity(context: Context) {
        TheRouter.build(Model1RoutePath.Model1ActivityPATH)
            .withInt("key1", 12345678)
            .withString("key2", "参数")
            .withBoolean("key3", false)
            .navigation(context)
    }

    fun navigationModel1RvActivity(context: Context) {
        TheRouter.build(Model1RoutePath.Model1RvActivityPATH)
            .navigation(context)
    }
}