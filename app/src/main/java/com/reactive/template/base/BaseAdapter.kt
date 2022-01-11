package com.reactive.template.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.reactive.template.utils.common.ViewHolder
import com.reactive.template.utils.extensions.inflate

abstract class BaseAdapter<T, V : ViewBinding> : RecyclerView.Adapter<BaseAdapter<T, V>.ViewBindingHolder>() {

    var listener: ((data: T) -> Unit)? = null
    protected var items = listOf<T>()
    open fun setData(data: List<T>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewBindingHolder(getBinding(LayoutInflater.from(parent.context), parent, viewType))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewBindingHolder, position: Int) {
        holder.apply {
            bindViewHolder(this, items[holder.adapterPosition])
            holder.itemView.setOnClickListener {
                if (holder.adapterPosition != -1)
                    listener?.invoke(items[holder.absoluteAdapterPosition])
            }
        }
    }

    abstract fun getBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): V
    abstract fun bindViewHolder(holder: ViewBindingHolder, data: T)

    inner class ViewBindingHolder(val binding: V) : RecyclerView.ViewHolder(binding.root)
}