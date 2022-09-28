package com.lyf.scaffold

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lyf.scaffold.utils.Track

class WelcomeActivity : AppCompatActivity() {

//    lateinit var crashButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        LiveEventBus.get<String>("1111").observe(this) {
            Log.i("1111", (Looper.myLooper() == Looper.getMainLooper()).toString())
        }
        Thread {
            LiveEventBus.get<String>("1111").post("@@@@@@@@@@@")
        }.start()
        val a = mutableListOf("1")
        Firebase.crashlytics.setUserId("user123456789")
        Track.initTrack()//收集
        Track.setTrackListener(object : Track.TrackListener {
            override fun track(event: String, bundle: Bundle?) {
                //这个是调用// 最终会调用 logEvent，上报 Firebase 和 自己的监听事件
                //上传firebase&回到，可以上报自己的服务器
            }
        })


        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.text = "login"
        loginButton.setOnClickListener {
            Track.logEvent(Track.Event.LOGIN_CLICK)//登录事件采集
            Track.setUserProperty("name", "Dany") // 登录成功
            Track.setUserId("uid123456")
        }



        //
        val cusButton = findViewById<Button>(R.id.cusButton)
        cusButton.text = "cus"
        cusButton.setOnClickListener {
            Track.logEvent(Track.Event.Game_CLICK, Bundle().apply {
                putString("game_info", "Google版本|G4pro|王者荣耀|1|3|sdasffasdf|gsgsdf|用户:3453453534")
            })//点击游戏

        }

    }
}