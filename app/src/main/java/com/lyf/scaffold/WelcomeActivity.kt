package com.lyf.scaffold

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.lyf.scaffold.utils.DevUtils
import com.lyf.scaffold.utils.Track
import com.therouter.TheRouter

class WelcomeActivity : AppCompatActivity() {

//    lateinit var crashButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
//        LiveEventBus.get<String>("1111").observe(this) {
//            Log.i("1111", (Looper.myLooper() == Looper.getMainLooper()).toString())
//        }
//        Thread {
//            LiveEventBus.get<String>("1111").post("@@@@@@@@@@@")
//        }.start()
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
        }


        //
        val cusButton = findViewById<Button>(R.id.cusButton)
        cusButton.text = "cus"
        cusButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("author", "谷歌版本|蓝牙连接成功|王者荣耀|4.3.1|用户:3242334_connect_info_林英发")
            bundle.putString("title", "谷歌版本|蓝牙连接成功|王者荣耀|4.3.1|用户:3242334_connect_info_程序员")
            bundle.putString("number_of_pages", "谷歌版本|蓝牙连接成功|王者荣耀|4.3.1|用户:3242334_connect_info_1人")
            Track.logEvent("author_enevt", bundle)//点击游戏
            //要在 Android 设备上启用 Analytics 调试模式，请执行以下命令：
            // adb shell setprop debug.firebase.analytics.app com.lyf.scaffold
            //调试模式将保持启用状态，直至您通过执行以下命令明确将其停用：
            // adb shell setprop debug.firebase.analytics.app .none.
        }

        //
        // 传入参数可以通过注解 @Autowired 解析成任意类型，如果是对象建议传json
        // context 参数如果不传或传 null，会自动使用 application 替换
//        TheRouter.build("http://therouter.com/home")
//            .withInt("key1", 12345678)
//            .withString("key2", "参数")
//            .withBoolean("key3", false)
////            .withSerializable("key4", object)
////            .withObject("object", any) // 与上一个方法不同，这个方法可以传递任意对象，但是接收的地方对象类型请保证一致
//            .navigation(this);

        // 如果传入 requestCode，默认使用startActivityForResult启动Activity
//        .navigation(context, 123);

        // 如果要打开的是fragment，需要使用
//        .createFragment();
        val brand = DevUtils.getBrand()//获取厂商
        val deviceName = DevUtils.getDeviceName()//设备名称
        val manufacturer = DevUtils.getManufacturer()//获取设备制造商
        val modelName = DevUtils.getModelName()//设备型号

        Log.i(
            "devutils", """
            ${brand}
            ${deviceName}
            ${manufacturer}
            ${modelName}
        """.trimIndent()
        )
    }
}