package com.someca.count.ui.adapter

import android.annotation.SuppressLint
import androidx.core.widget.addTextChangedListener
import com.someca.count.R
import com.someca.count.base.BaseAdapter
import com.someca.count.bean.CashBean
import com.someca.count.databinding.ItemCashBinding

class CashAdapter(data : MutableList<CashBean>) : BaseAdapter<ItemCashBinding,CashBean>(data) {
    var onCalculateListener : ((CashBean)->Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun convert(binding: ItemCashBinding, item: CashBean) {
        binding.cash = item
        binding.cashEdit1.setText(item.num1.toString())
        binding.cashEdit2.setText(item.num2.toString())
        binding.cashResult.text = context.getString(R.string.qian)+item.result

        binding.cashJia.setOnClickListener {
            item.num2++
            binding.cashEdit2.setText(item.num2.toString())
        }

        binding.cashJian.setOnClickListener {
            item.num2 = (item.num2 - 1).coerceAtLeast(0)
            binding.cashEdit2.setText(item.num2.toString())
        }

        binding.cashEdit1.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            item.num1 = it.toString().toInt()
            item.result = item.num1*item.num2
            binding.cashResult.text = context.getString(R.string.qian)+item.result
            notifyItemChanged(getItemPosition(item),0)
        }

        binding.cashEdit2.addTextChangedListener {
            if (it.isNullOrBlank()) return@addTextChangedListener
            item.num2 = it.toString().toInt()
            item.result = item.num1*item.num2
            binding.cashResult.text = context.getString(R.string.qian)+item.result
            notifyItemChanged(getItemPosition(item),0)
        }
    }

}