package com.mvvm.coroutines.base

interface RecyclerListener<D> {

    fun onClicked(position: Int, data: D?)

}