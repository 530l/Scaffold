package com.lyf.common

import android.os.Bundle
import com.lyf.base.BaseActivity
import com.therouter.TheRouter

abstract class CommBaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheRouter.inject(this)
    }
}