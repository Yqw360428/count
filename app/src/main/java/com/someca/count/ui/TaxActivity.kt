package com.someca.count.ui

import androidx.core.widget.addTextChangedListener
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.TaxBean
import com.someca.count.databinding.ActivityTaxBinding
import com.someca.count.utils.fixNum
import com.someca.count.utils.initPopup
import com.someca.count.utils.setOnSingleClick

class TaxActivity : BaseActivity<ActivityTaxBinding>() {
    private var type = 0
    private var amount = 0f
    private var rate = 0f
    private var charging = 0
    override fun initView() {
        intent.extras?.getInt("type")?.let {
            type = it
        }
        binding.type = type
        binding.bar.barTitle.text = getString(if (type == 0) R.string.gst else R.string.vat)
        binding.tax = TaxBean()
        binding.half = ""

        binding.bar.barBack.setOnSingleClick {
            finish()
        }

        binding.taxAmount.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            amount = it.toString().toFloat()
        }

        binding.taxRate.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            rate = it.toString().toFloat()
            binding.half = (rate/2).fixNum().toString()+getString(R.string.per)
        }
        val chargingText = if (type == 0) arrayOf(
            getString(R.string.add_gst),
            getString(R.string.subtract_gst)
        ) else arrayOf(
            getString(R.string.add_vat),
            getString(R.string.subtract_vat)
        )

        binding.taxSelectCharging.setOnSingleClick {
            it.initPopup(chargingText){index,str->
                charging = index
                binding.taxCharging.text = str
            }
        }


        binding.taxReset.setOnSingleClick {
            binding.taxAmount.setText("")
            binding.taxRate.setText("")
            binding.taxCharging.text = ""
            charging = 0
            amount = 0f
            rate = 0f
            binding.half = ""
            binding.tax = TaxBean()
        }

        binding.taxCalculate.setOnSingleClick {
            if (amount == 0f) return@setOnSingleClick
            if (rate == 0f) return@setOnSingleClick
            val taxNum: Float = if (charging == 0){
                amount*rate/100
            }else{
                amount*rate/(100+rate)
            }
            binding.tax = TaxBean((taxNum/2).fixNum(),taxNum.fixNum(),(if (charging == 0) taxNum+amount else amount-taxNum).fixNum())
        }
    }
}