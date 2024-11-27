package com.someca.count.ui

import androidx.lifecycle.lifecycleScope
import com.someca.count.base.BaseActivity
import com.someca.count.databinding.ActivityStartBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity<ActivityStartBinding>() {
    override fun initView() {
        lifecycleScope.launch {
            delay(3000)
            launch(MainActivity::class.java)
        }
    }
}