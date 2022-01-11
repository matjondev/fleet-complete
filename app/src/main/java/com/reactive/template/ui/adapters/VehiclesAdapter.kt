package com.reactive.template.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.reactive.template.R
import com.reactive.template.base.BaseAdapter
import com.reactive.template.databinding.ItemVehiclesBinding
import com.reactive.template.network.models.LastData
import com.reactive.template.utils.common.calculateDate

class VehiclesAdapter : BaseAdapter<LastData, ItemVehiclesBinding>(){
    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemVehiclesBinding {
        return ItemVehiclesBinding.inflate(inflater,parent,false)
    }

    override fun bindViewHolder(holder: ViewBindingHolder, data: LastData) {
        holder.binding.apply {
            data.apply {

                titleTxt.text = plate + if (driverName.isNullOrBlank()) "" else " / $driverName"
                addressTxt.text = address
                speedTxt.text = holder.itemView.context.getString(R.string.s_kmh,speed)

                val time = calculateDate(lastEngineOnTime,timestamp)
                dataAgeTxt.text = holder.itemView.context.getString(R.string.s_ago, time)
            }
        }
    }


}