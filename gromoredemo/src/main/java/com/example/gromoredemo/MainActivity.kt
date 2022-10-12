package com.example.gromoredemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //todo gromore sdk 需要2-3s左右初始化完成。。。。
        Thread.sleep(3000)
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }
}