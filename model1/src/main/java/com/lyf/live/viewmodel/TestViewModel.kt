package com.lyf.live.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.scopeLife
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.kunminx.architecture.domain.message.MutableResult
import com.lyf.live.bean.DataVO
import com.lyf.live.bean.TestM1
import kotlinx.coroutines.delay


class TestViewModel : ViewModel() {

    private val testM1Result: MutableResult<DataVO> = MutableResult<DataVO>()


    fun getTestM1Result(): MutableResult<DataVO> {
        return testM1Result
    }


     fun requestXXX(page:Int) {
        //业务逻辑 ...
        scopeNetLife {
            val dataVO = Get<DataVO?>("/article/list/0/json").await()
            dataVO?.let {
                testM1Result.value = it
            }
        }
    }
}