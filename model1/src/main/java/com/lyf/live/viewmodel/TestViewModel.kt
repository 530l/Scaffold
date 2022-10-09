package com.lyf.live.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.scopeLife
import androidx.lifecycle.scopeNetLife
import com.kunminx.architecture.domain.message.MutableResult
import com.lyf.live.bean.TestM1
import kotlinx.coroutines.delay


class TestViewModel : ViewModel() {

    private val testM1Result: MutableResult<TestM1> = MutableResult<TestM1>()


    fun getTestM1Result(): MutableResult<TestM1> {
        return testM1Result
    }

    init {
        scopeLife {
            delay(2000)
            requestXXX()
        }
    }

    private fun requestXXX() {
        //业务逻辑 ...
        testM1Result.value = TestM1("request xxxx")
    }
}