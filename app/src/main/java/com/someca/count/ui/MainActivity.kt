package com.someca.count.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.someca.count.App
import com.someca.count.base.BaseActivity
import com.someca.count.databinding.ActivityMainBinding
import com.someca.count.utils.setOnSingleClick

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initView() {
        binding.run {
            setOnSingleClick(mainSet, mainBasic, mainAdvanced, mainGst, mainVat, mainCash,mainPrivacy,mainOpen) {
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
                    mainPrivacy-> launch(WebActivity::class.java,Bundle().apply {
                        putString("url","https://www.financesimplified.pro/finance-simplified-privacy")
                    })
                    mainOpen->launch(WebActivity::class.java, Bundle().apply {
                        putString("url",App.initBean!!.cheap!!.alteration)
                    })
                }
            }
            mainOpen.isVisible = App.initBean != null
        }
    }

}