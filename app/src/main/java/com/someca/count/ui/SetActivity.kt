package com.someca.count.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.someca.count.BuildConfig
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.databinding.ActivitySetBinding
import com.someca.count.utils.copyTextToClipboard
import com.someca.count.utils.setOnSingleClick
import com.someca.count.utils.toastShort

class SetActivity : BaseActivity<ActivitySetBinding>() {
    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.bar.barBack.setOnSingleClick {
            finish()
        }

        binding.bar.barTitle.text = getString(R.string.settings)

        binding.setPhone.setOnSingleClick {
            binding.setPhone.copyTextToClipboard()
            "Copied successfully".toastShort
        }

        binding.setEmail.setOnSingleClick {
            binding.setEmail.copyTextToClipboard()
            "Copied successfully".toastShort
        }

        binding.setPrivacy.setOnSingleClick {
            launch(WebActivity::class.java, Bundle().apply {
                putString("url","https://www.financesimplified.pro/finance-simplified-privacy")
            })
        }

        binding.setVersion.text = "Version:${BuildConfig.VERSION_NAME}"
    }
}