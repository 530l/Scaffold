package com.lyf.live

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.drake.brv.utils.*
import com.dylanc.viewbinding.binding
import com.lyf.common.CommBaseActivity
import com.lyf.export_data.model1.Model1RoutePath
import com.lyf.live.bean.DataVO
import com.lyf.live.bean.DataVO.DatasVO
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

    var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.linear().setup {
            addType<DatasVO>(R.layout.model1_adapter_item_simple)
            onBind {

            }
        }

        val pageCreate = binding.recyclerView.pageCreate()

        pageCreate.onRefresh {
            page = 0
            viewModel.requestXXX(page)
        }.autoRefresh()

        pageCreate.onLoadMore {
            page++
            viewModel.requestXXX(page)
        }


        viewModel.getTestM1Result().observe(this) {
            //
            if (page == 0 && binding.recyclerView.models != null
                && binding.recyclerView.mutable.isNotEmpty()
            ) {
                binding.recyclerView.mutable.clear()
            }

            if (!it.datas.isNullOrEmpty()) {
                binding.recyclerView.addModels(it.datas)
            }

            pageCreate.finishLoadMore()
            pageCreate.finishRefresh()
        }

        key2?.let { Log.i("Interceptor", it) }
    }


}