package com.lyf.live

import android.os.Bundle
import android.util.Log
import com.dylanc.viewbinding.binding
import com.lyf.common.CommBaseActivity
import com.lyf.export_data.model1.Model1RoutePath
import com.lyf.live.databinding.Model1ActivityModel1Binding
import com.therouter.router.Autowired
import com.therouter.router.Route

@Route(path = Model1RoutePath.Model1ActivityPATH)
class Model1Activity : CommBaseActivity() {

    private val binding: Model1ActivityModel1Binding by binding()

    @Autowired
    @JvmField
    var key1: Int = 0

    @Autowired
    @JvmField
    var key2: String? = ""

    @Autowired
    @JvmField
    var key3: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            btn1.text = key1.toString()
            btn2.text = key2
            btn3.text = key3.toString()
        }
    }
}