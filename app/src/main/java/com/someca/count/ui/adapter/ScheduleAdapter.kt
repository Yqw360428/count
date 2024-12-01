package com.someca.count.ui.adapter

import com.someca.count.base.BaseAdapter
import com.someca.count.bean.ScheduleBean
import com.someca.count.databinding.ItemScheduleBinding

class ScheduleAdapter(data : MutableList<ScheduleBean>) : BaseAdapter<ItemScheduleBinding,ScheduleBean>(data) {
    override fun convert(binding: ItemScheduleBinding, item: ScheduleBean) {
        binding.schedule = item
    }
}