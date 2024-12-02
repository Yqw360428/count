package com.someca.count.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.ScheduleBean
import com.someca.count.databinding.ActivityScheduleBinding
import com.someca.count.ui.adapter.ScheduleAdapter
import com.someca.count.utils.calculateEMIRepaymentSchedule
import com.someca.count.utils.setOnSingleClick

class ScheduleActivity : BaseActivity<ActivityScheduleBinding>() {
    private val schedulerList = mutableListOf<ScheduleBean>()
    private val scheduleAdapter by lazy {
        ScheduleAdapter(schedulerList).apply {
            setHasStableIds(true)
        }
    }

    private var amount = 0.0
    private var rate = 0.0
    private var tenure = 0

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        binding.bar.barTitle.text = getString(R.string.repayment_schedule)

        intent.extras?.run {
            amount = getDouble("amount")
            rate = getDouble("rate")
            tenure = getInt("tenure")
        }

        binding.bar.barBack.setOnSingleClick {
            finish()
        }

        binding.rvSchedule.apply {
            layoutManager = LinearLayoutManager(this@ScheduleActivity)
            adapter = scheduleAdapter
        }

        schedulerList.addAll(calculateEMIRepaymentSchedule(amount,rate,tenure))
        scheduleAdapter.notifyDataSetChanged()
    }
}