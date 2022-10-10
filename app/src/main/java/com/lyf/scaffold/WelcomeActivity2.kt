package com.lyf.scaffold

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.drake.net.utils.scopeLife
import com.dylanc.viewbinding.binding
import com.lyf.base.BaseActivity
import com.lyf.common.db.WordDB
import com.lyf.common.db.WordDao
import com.lyf.common.db.WordEntity
import com.lyf.export_data.di.Record
import com.lyf.export_data.model1.Model1Route
import com.lyf.scaffold.databinding.ActivityWelcome2Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity2 : BaseActivity() {

    private val binding: ActivityWelcome2Binding by binding()

    private lateinit var wordDao: WordDao

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
        binding.btn3.setOnClickListener {



        }
        binding.btn4.setOnClickListener { }
        binding.btn5.setOnClickListener { }
        binding.btn6.setOnClickListener { }
        binding.btn7.setOnClickListener { }
        binding.btn8.setOnClickListener { }
        binding.btn9.setOnClickListener { }

        Log.i("hiltDi", record?.age.toString())
        record?.age = 1

        wordDao = WordDB.getInstance(this)?.getWordDao() ?: return
        scopeLife {
            wordDao.insert(WordEntity("it"))

        }
    }
}