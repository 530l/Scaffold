package com.lyf.scaffold

import android.graphics.Color
import android.os.Bundle
import com.dylanc.viewbinding.binding
import com.lyf.base.BaseActivity
import com.lyf.export_data.model1.Model1Route
import com.lyf.scaffold.databinding.ActivityWelcome2Binding

class WelcomeActivity2 : BaseActivity() {

    private val binding: ActivityWelcome2Binding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root.setBackgroundColor(Color.YELLOW)
        binding.btn1.setOnClickListener {
            Model1Route.navigationModel1Activity(this)
        }
        binding.btn2.setOnClickListener { }
        binding.btn3.setOnClickListener { }
        binding.btn4.setOnClickListener { }
        binding.btn5.setOnClickListener { }
        binding.btn6.setOnClickListener { }
        binding.btn7.setOnClickListener { }
        binding.btn8.setOnClickListener { }
        binding.btn9.setOnClickListener { }
    }
}