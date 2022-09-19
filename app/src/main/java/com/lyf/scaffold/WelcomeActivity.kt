package com.lyf.scaffold

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.jeremyliao.liveeventbus.LiveEventBus

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        LiveEventBus.get<String>("1111").observe(this){
            Log.i("1111", (Looper.myLooper() == Looper.getMainLooper()).toString())
        }
        Thread{
            LiveEventBus.get<String>("1111").post("@@@@@@@@@@@")
        }.start()
    }
}