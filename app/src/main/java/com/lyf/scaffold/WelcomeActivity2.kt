package com.lyf.scaffold

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.dylanc.viewbinding.binding
import com.lyf.base.BaseActivity
import com.lyf.export_data.di.Record
import com.lyf.export_data.model1.Model1Route
import com.lyf.scaffold.adn.AdnBannerActivity
import com.lyf.scaffold.adn.AdnInsertActivity
import com.lyf.scaffold.adn.AdnSplashActivity
import com.lyf.scaffold.databinding.ActivityWelcome2Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity2 : BaseActivity() {

    private val binding: ActivityWelcome2Binding by binding()


    @Inject
    @JvmField
    var record: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root.setBackgroundColor(Color.YELLOW)
        binding.btn1.setOnClickListener {
            Model1Route.navigationModel1Activity(this)
        }
        binding.btn2.setOnClickListener {
            Model1Route.navigationModel1RvActivity(this)
        }
        binding.btn3.text = "AdnSplash"
        binding.btn3.setOnClickListener {
            val intent = Intent(this, AdnSplashActivity::class.java)
            startActivity(intent)
        }
        binding.btn4.text = "AdnInsert"
        binding.btn4.setOnClickListener {
            val intent = Intent(this, AdnInsertActivity::class.java)
            startActivity(intent)
        }
        binding.btn5.text = "AdnBanner"
        binding.btn5.setOnClickListener {
            val intent = Intent(this, AdnBannerActivity::class.java)
            startActivity(intent)
        }
        binding.btn6.setOnClickListener { }
        binding.btn7.setOnClickListener { }
        binding.btn8.setOnClickListener { }
        binding.btn9.setOnClickListener { }

        Log.i("hiltDi", record?.age.toString())
        record?.age = 1
    }
}