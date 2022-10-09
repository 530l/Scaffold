package com.lyf.live

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.dylanc.viewbinding.binding
import com.lyf.common.CommBaseActivity
import com.lyf.export_data.model1.Model1RoutePath
import com.lyf.live.databinding.Model1RvActivityBinding
import com.lyf.live.viewmodel.TestViewModel
import com.therouter.router.Autowired
import com.therouter.router.Route

@Route(path = Model1RoutePath.Model1RvActivityPATH)
class Model1RvActivity : CommBaseActivity() {

    private val binding: Model1RvActivityBinding by binding()

    private val viewModel: TestViewModel by viewModels()

    @Autowired
    @JvmField
    var key2: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTestM1Result().observe(this) {
            Log.i("TestViewModel", it.title)
        }

        viewModel.getTestM1Result().observeForever {

        }

        viewModel.getTestM1Result().observeSticky(this) {

        }

        key2?.let { Log.i("Interceptor", it) }
    }
}