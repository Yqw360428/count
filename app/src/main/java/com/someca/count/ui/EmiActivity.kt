package com.someca.count.ui

import androidx.core.widget.addTextChangedListener
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.EmiResultBean
import com.someca.count.databinding.ActivityEmiBinding
import com.someca.count.utils.fixNum
import com.someca.count.utils.initPopup
import com.someca.count.utils.setOnSingleClick

class EmiActivity : BaseActivity<ActivityEmiBinding>() {
    private var type = 0
    private var tenureType = 0 //0 years 1 months
    private var schemeType = 0 //0 arrear 1 advance
    private var amount = 0
    private var rate = 0f
    private var tenure = 0
    private var free = 0

    override fun initView() {
        intent.extras?.getInt("type")?.let {
            type = it
        }
        binding.type = type
        binding.bar.barTitle.text = getString(if (type == 0) R.string.emi_basic else R.string.emi_advanced)
        binding.result = EmiResultBean()

        binding.bar.barBack.setOnSingleClick {
            finish()
        }

        binding.emiAmount.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            amount = it.toString().toInt()
        }

        binding.emiRate.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            rate = it.toString().toFloat()
        }

        binding.emiTenure.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            tenure = it.toString().toInt()
        }

        binding.emiFree.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            free = it.toString().toInt()
        }

        binding.emiSelectTen.setOnSingleClick {
            it.initPopup(arrayOf(getString(R.string.years),getString(R.string.months))){index,_->
                tenureType = index
                if (tenureType == 1) rate /= 12
            }
        }

        binding.emiSelectScheme.setOnSingleClick {
            it.initPopup(arrayOf(getString(R.string.arrear_emi),getString(R.string.advance_emi))){index,str->
                schemeType = index
                binding.emiScheme.text = str
            }
        }

        binding.emiReset.setOnSingleClick {
            binding.result = EmiResultBean()
            binding.emiAmount.setText("")
            binding.emiRate.setText("")
            binding.emiTenure.setText("")
            binding.emiFree.setText("")
            binding.emiScheme.text = ""
            amount = 0
            rate = 0f
            tenure = 0
            free = 0
            binding.selectTenureText.text = getString(R.string.years)
            tenureType = 0
            schemeType = 0
        }

        binding.emiCalculate.setOnSingleClick {
            if (amount == 0) return@setOnSingleClick
            if (rate == 0f) return@setOnSingleClick
            if (tenure == 0) return@setOnSingleClick
            if (type == 0){
                val interest = (amount*tenure*rate/100).fixNum()
                binding.result = EmiResultBean(interest,((interest+amount)/(if (tenureType == 0) tenure*12 else tenure)).fixNum(),(amount+interest).fixNum())
            }else{

            }
        }

    }
}