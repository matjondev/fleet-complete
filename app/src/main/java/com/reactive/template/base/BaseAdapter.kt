package com.reactive.template.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.reactive.template.utils.common.ViewHolder
import com.reactive.template.utils.extensions.inflate

abstract class BaseAdapter<T>(@LayoutRes val layoutID: Int) : RecyclerView.Adapter<ViewHolder>() {

    var listener: ((data: Any) -> Unit)? = null
    protected var items = listOf<T>()
    open fun setData(data: List<T>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(layoutID))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindViewHolder(this, items[holder.adapterPosition])
            holder.itemView.setOnClickListener {
                if (holder.adapterPosition != -1)
                    listener?.invoke(this)
            }
        }
    }

    abstract fun bindViewHolder(holder: ViewHolder, data: T)

}