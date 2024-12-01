package com.someca.count.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.someca.count.R
import com.someca.count.base.BaseActivity
import com.someca.count.bean.ScheduleBean
import com.someca.count.databinding.ActivityScheduleBinding
import com.someca.count.ui.adapter.ScheduleAdapter
import com.someca.count.utils.setOnSingleClick

class ScheduleActivity : BaseActivity<ActivityScheduleBinding>() {
    private val schedulerList = mutableListOf<ScheduleBean>()
    private val scheduleAdapter by lazy {
        ScheduleAdapter(schedulerList).apply {
            setHasStableIds(true)
        }
    }

    override fun initView() {
        binding.bar.barTitle.text = getString(R.string.repayment_schedule)

        binding.bar.barBack.setOnSingleClick {
            finish()
        }

        binding.rvSchedule.apply {
            layoutManager = LinearLayoutManager(this@ScheduleActivity)
            adapter = scheduleAdapter
        }


    }
}