package com.example.gromoredemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.gromoredemo.config.GMAdManagerHolder;

/**
 * created by wuzejian on 2020-03-12
 */
public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //MSDK的初始化需要放在Application中进行
        GMAdManagerHolder.init(this);
        Log.d("App", "App-->onCreate-0<TTAdManagerHolder.init");
        //启用phone 端调试工具。必须在onCreate 之后调用。
//        DigGo.showLens(this);// optional 【可选】
    }


    public static Context getAppContext() {
        return mContext;
    }
}
