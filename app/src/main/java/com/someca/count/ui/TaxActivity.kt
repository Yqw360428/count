package com.someca.count.ui

import androidx.core.widget.addTextChangedListener
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.TaxBean
import com.someca.count.databinding.ActivityTaxBinding
import com.someca.count.utils.initPopup
import com.someca.count.utils.setOnSingleClick
import kotlin.math.round

class TaxActivity : BaseActivity<ActivityTaxBinding>() {
    private var type = 0
    private var amount = 0.0
    private var rate = 0.0
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
            amount = it.toString().toDouble()
        }

        binding.taxRate.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            rate = it.toString().toDouble()
            binding.half = (rate/2).toString()+getString(R.string.per)
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
            amount = 0.0
            rate = 0.0
            binding.half = ""
            binding.tax = TaxBean()
        }

        binding.taxCalculate.setOnSingleClick {
            if (amount == 0.0) return@setOnSingleClick
            if (rate == 0.0) return@setOnSingleClick
            val taxNum = if (charging == 0){
                amount*rate/100
            }else{
                amount*rate/(100+rate)
            }
            binding.tax = TaxBean(round(taxNum/2).toInt(), round(taxNum).toInt(),
                round(if (charging == 0) taxNum+amount else amount-taxNum).toInt())
        }
    }
}