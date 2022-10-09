package com.lyf.live

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.angcyo.tablayout.TabGradientCallback
import com.dylanc.viewbinding.binding
import com.lyf.common.CommBaseActivity
import com.lyf.export_data.di.Record
import com.lyf.export_data.model1.Model1RoutePath
import com.lyf.live.databinding.Model1ActivityModel1Binding
import com.lyf.live.flowtask.TestLifecycle
import com.therouter.TheRouter
import com.therouter.router.Autowired
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = Model1RoutePath.Model1ActivityPATH)
@AndroidEntryPoint
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

    //todo hilt，。好东西，可以看看，不过引入的时候需要注意，
    // 每一个模块都需要声明插件，引入，注解处理器等等。不能省略
    // 通过注入的对象，就不用自己管理声明周期了，可以交给框架了
    @Inject
    @JvmField
    var record: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            btn1.text = key1.toString()
            btn1.setOnClickListener {
                // 当用户同意隐私协议时，调度依赖隐私协议的所有任务执行
                TheRouter.runTask("AgreePrivacyCache")
            }
            btn2.text = key2
            btn2.setOnClickListener {
                // 如果执行了一个没有被声明的Action，则不会有任何动作
                TheRouter.build(TestLifecycle.ACTION1).action()

            }
            btn3.text = key3.toString()
            btn3.setOnClickListener {
                TheRouter.build(TestLifecycle.ACTION2).action()
            }

            dslTabLayout1.setCurrentItem(2)
            dslTabLayout1.configTabLayoutConfig {
                /**是否开启文本颜色*/
                tabEnableTextColor = true
                /**是否开启颜色渐变效果*/
                tabEnableGradientColor = false
                /**选中的文本颜色*/
                tabSelectColor = Color.WHITE //Color.parseColor("#333333")
                /**未选中的文本颜色*/
                tabDeselectColor = Color.parseColor("#999999")
                /**是否开启Bold, 文本加粗*/
                tabEnableTextBold = false
                /**是否开启图标颜色*/
                tabEnableIcoColor = true
                /**是否开启图标颜色渐变效果*/
                tabEnableIcoGradientColor = false
                /**是否开启scale渐变效果*/
                tabEnableGradientScale = false
                /**最小缩放的比例*/
                tabMinScale = 0.8f
                /**最大缩放的比例*/
                tabMaxScale = 1.2f
                tabGradientCallback = TabGradientCallback()
                onSelectIndexChange = { fromIndex, selectIndexList, reselect, fromUser ->
                    val toIndex = selectIndexList.first()
                    Log.i("dslTabLayout1", "toIndex=${toIndex}")
                    Log.i("dslTabLayout1", "fromIndex=${fromIndex}")
                }
            }
        }

        Log.i("hiltDi", record?.age.toString())

    }
}