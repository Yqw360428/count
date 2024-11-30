package com.someca.count.ui

import android.os.Bundle
import com.someca.count.base.BaseActivity
import com.someca.count.databinding.ActivityMainBinding
import com.someca.count.utils.setOnSingleClick

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initView() {
        binding.run {
            setOnSingleClick(mainSet, mainBasic, mainAdvanced, mainGst, mainVat, mainCash) {
                when (it) {
                    mainSet -> launch(SetActivity::class.java)

                    mainBasic -> launch(EmiActivity::class.java, Bundle().apply {
                        putInt("type", 0)
                    })

                    mainAdvanced -> launch(EmiActivity::class.java, Bundle().apply {
                        putInt("type", 1)
                    })

                    mainGst -> launch(TaxActivity::class.java, Bundle().apply {
                        putInt("type", 0)
                    })

                    mainVat -> launch(TaxActivity::class.java, Bundle().apply {
                        putInt("type", 1)
                    })

                    mainCash -> launch(CashActivity::class.java)
                }
            }
        }
    }

}