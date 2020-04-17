package com.mvvm.coroutines.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, V : BaseViewHolder<T, *>>(var data: MutableList<T>?) :
    RecyclerView.Adapter<V>() {

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.wholeList = data
        holder.lastItemPosition = itemCount - 1
        holder.data = getItem(position)
        holder.listLength = itemCount
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getItem(position: Int): T? {
        return data?.get(position)
    }

    fun addItem(`object`: T) {
        data?.add(`object`)
        data?.indexOf(`object`)?.let {
            notifyItemInserted(it)
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    fun removeItem(position: Int) {
        data?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun isItemRemoved(position: Int) = if (data?.size ?: 0 >= position + 1) {
        data?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        true
    } else false

    private fun resetItems(data: MutableList<T>?) {
        if (data != null) {
            this.data = data
            notifyDataSetChanged()
        }
    }

    fun addItems(data: List<T>?) {
        if (data != null) {
            val startRange = if (this.data?.size ?: 0 - 1 > 0) data.size - 1 else 0
            this.data?.addAll(data)
            notifyItemRangeChanged(startRange, data.size)
        }
    }

    fun setFilter(data: MutableList<T>) {
        this.data = ArrayList()
        this.data?.addAll(data)
        resetItems(data)
    }

    fun inflateDataBinding(layout: Int, parent: ViewGroup): ViewDataBinding? = DataBindingUtil.bind(
        LayoutInflater.from(parent.context).inflate(layout, parent, false)
    )
}