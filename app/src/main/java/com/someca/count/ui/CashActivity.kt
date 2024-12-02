package com.someca.count.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.CashBean
import com.someca.count.databinding.ActivityCashBinding
import com.someca.count.ui.adapter.CashAdapter
import com.someca.count.utils.setOnSingleClick

class CashActivity : BaseActivity<ActivityCashBinding>() {
    private val cashList = mutableListOf<CashBean>()
    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.bar.barTitle.text = getString(R.string.cash)
        resetData()

        binding.bar.barBack.setOnSingleClick {
            finish()
        }

        binding.cashReset.setOnSingleClick {
            resetData()
            binding.cashResult.text = getString(R.string.qian)+"0"
        }

        binding.cashCalculate.setOnSingleClick {
            binding.cashResult.text = getString(R.string.qian) + cashList.sumOf { it.result }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetData(){
        cashList.clear()
        val cashAdapter = CashAdapter(cashList)
        binding.rvCash.apply {
            layoutManager = LinearLayoutManager(this@CashActivity)
            adapter = cashAdapter
        }
        cashList.apply {
            add(CashBean(isCustom = false, num1 = 500, num2 = 0, result = 0))
            add(CashBean(isCustom = false, num1 = 200, num2 = 0, result = 0))
            add(CashBean(isCustom = false, num1 = 100, num2 = 0, result = 0))
            add(CashBean(isCustom = false, num1 = 50,num2 = 0,result =0))
            add(CashBean(isCustom = false,num1 =20,num2 =0,result =0))
            add(CashBean(isCustom = false,num1 =10,num2 =0,result =0))
            add(CashBean(isCustom = false,num1 =5,num2 =0,result =0))
            add(CashBean(isCustom = false,num1 =2,num2 =0,result =0))
            add(CashBean(isCustom = false,num1 =1,num2 =0,result =0))
            add(CashBean(isCustom = true,num1 =0,num2 =0,result =0))
        }
        cashAdapter.notifyDataSetChanged()
    }
}