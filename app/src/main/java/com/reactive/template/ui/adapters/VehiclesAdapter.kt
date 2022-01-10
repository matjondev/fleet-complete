package com.reactive.template.ui.adapters

import com.reactive.template.R
import com.reactive.template.base.BaseAdapter
import com.reactive.template.utils.common.ViewHolder

class VehiclesAdapter : BaseAdapter<Any>(R.layout.item_vehicles) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            data.apply {
            }
        }
    }

}