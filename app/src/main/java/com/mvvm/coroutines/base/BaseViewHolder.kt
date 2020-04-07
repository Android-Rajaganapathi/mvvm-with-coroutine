package com.mvvm.coroutines.base

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.coroutines.common.Snippet

abstract class BaseViewHolder<D, VB : ViewDataBinding> : RecyclerView.ViewHolder {
    var codeSnippet = Snippet(itemView.context)
    var data: D? = null
        set(value) {
            field = value
            data?.let { populateData(it) }
        }
    var wholeList: MutableList<D>? = null
    protected lateinit var mBinding: VB
    open var lastItemPosition = 0
    fun getPreviousItem(): D? {
        return if (adapterPosition != 0)
            wholeList?.get(adapterPosition - 1)
        else null
    }

    open var listLength =
        if (data is MutableList<*> && !(data as MutableList<*>).isNullOrEmpty()) (data as MutableList<*>).size - 1 else 0

    constructor(viewDataBinding: VB) : super(viewDataBinding.root) {
        this.mBinding = viewDataBinding
    }

    constructor(itemView: View) : super(itemView)

    abstract fun populateData(data: D)
}