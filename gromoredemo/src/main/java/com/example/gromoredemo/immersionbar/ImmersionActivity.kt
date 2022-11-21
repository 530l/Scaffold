package com.example.gromoredemo.immersionbar

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gromoredemo.R
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

class ImmersionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getContentView()?.let {
            ImmersionBar.with(this)
                .titleBar(obtainTitleBar(it))
//                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .fullScreen(true)
                .init();
        }


    }

    /**
     * 和 setContentView 对应的方法
     */
    private fun getContentView(): ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    /**
     * 递归获取 ViewGroup 中的 TitleBar 对象
     */
    private fun obtainTitleBar(group: ViewGroup?): TextView? {
        if (group == null) {
            return null
        }
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is TextView) {
                return view
            }
            if (view is ViewGroup) {
                val titleBar = obtainTitleBar(view)
                if (titleBar != null) {
                    return titleBar
                }
            }
        }
        return null
    }
}