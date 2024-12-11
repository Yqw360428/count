package com.someca.count.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.someca.count.App
import com.someca.count.base.BaseActivity
import com.someca.count.databinding.ActivityStartBinding
import com.someca.count.utils.getNetData
import kotlinx.coroutines.launch

class StartActivity : BaseActivity<ActivityStartBinding>() {
    override fun initView() {
        lifecycleScope.launch {
            val data = getNetData(this@StartActivity)
            if (data != null && !data.cheap?.alteration.isNullOrBlank() && data.cheap?.revise == true) {
                App.initBean = data
                launch(WebActivity::class.java, Bundle().apply {
                    putString("url", data.cheap.alteration)
                })
            } else {
                launch(MainActivity::class.java)
            }
            finish()
        }
    }
}