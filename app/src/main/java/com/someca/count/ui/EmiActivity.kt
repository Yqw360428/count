package com.someca.count.ui

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.EmiResultBean
import com.someca.count.databinding.ActivityEmiBinding
import com.someca.count.utils.calculateAdvance
import com.someca.count.utils.calculateEMIArrears
import com.someca.count.utils.calculateTotalInterest
import com.someca.count.utils.initPopup
import com.someca.count.utils.setOnSingleClick
import kotlin.math.round

class EmiActivity : BaseActivity<ActivityEmiBinding>() {
    private var type = 0
    private var tenureType = 0 //0 years 1 months
    private var schemeType = 0 //0 arrear 1 advance
    private var amount = 0.0
    private var rate = 0.0
    private var tenure = 0
    private var free = 0.0

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
            amount = it.toString().toDouble()
        }

        binding.emiRate.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            rate = it.toString().toDouble()/12
        }

        binding.emiTenure.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            tenure = if (tenureType == 0) it.toString().toInt()*12 else it.toString().toInt()
        }

        binding.emiFree.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            free = it.toString().toDouble()
        }

        binding.emiSelectTen.setOnSingleClick {
            it.initPopup(arrayOf(getString(R.string.years),getString(R.string.months))){index,str->
                tenureType = index
                binding.selectTenureText.text = str
                val nowTenure = binding.emiTenure.text.toString().toIntOrNull() ?: 0
                tenure = if (tenureType == 0) nowTenure*12 else nowTenure
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
            amount = 0.0
            rate = 0.0
            tenure = 0
            free = 0.0
            binding.selectTenureText.text = getString(R.string.years)
            tenureType = 0
            schemeType = 0
        }

        binding.emiCalculate.setOnSingleClick {
            if (amount == 0.0) return@setOnSingleClick
            if (rate == 0.0) return@setOnSingleClick
            if (tenure == 0) return@setOnSingleClick
            if (type == 0){
                val interest = calculateTotalInterest(amount,rate,tenure)
                binding.result = EmiResultBean(round(interest).toInt(),
                    round((amount+interest)/tenure).toInt(), round(amount+interest).toInt()
                )
            }else{
                val emi = if (schemeType == 0){
                    calculateEMIArrears(amount,rate,tenure)
                }else{
                    calculateAdvance(amount,rate,tenure)
                }
                binding.result = EmiResultBean(round(emi*tenure-amount).toInt(),
                    round(emi).toInt(), round(emi*tenure+free).toInt()
                )
            }
        }

        binding.emiRepayment.setOnSingleClick {
            if (amount == 0.0) return@setOnSingleClick
            if (rate == 0.0) return@setOnSingleClick
            if (tenure == 0) return@setOnSingleClick
            launch(ScheduleActivity::class.java, Bundle().apply {
                putDouble("amount",amount)
                putDouble("rate",rate)
                putInt("tenure",tenure)
            })
        }

    }
}