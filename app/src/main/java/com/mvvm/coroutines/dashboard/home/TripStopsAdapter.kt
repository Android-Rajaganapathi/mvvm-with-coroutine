package com.mvvm.coroutines.dashboard.home

import android.view.ViewGroup
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseAdapter
import com.mvvm.coroutines.base.BaseViewHolder
import com.mvvm.coroutines.base.RecyclerListener
import com.mvvm.coroutines.data.model.UserResponse
import com.mvvm.coroutines.databinding.ItemUserBinding

class TripStopsAdapter(
    data: MutableList<UserResponse>,
    var mListener: RecyclerListener<UserResponse>
) : BaseAdapter<UserResponse, TripStopViewHolder>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TripStopViewHolder(
            inflateDataBinding(R.layout.item_user, parent) as ItemUserBinding,
            mListener
        )
}

class TripStopViewHolder(
    view: ItemUserBinding,
    var listener: RecyclerListener<UserResponse>
) : BaseViewHolder<UserResponse, ItemUserBinding>(view) {

    override fun populateData(data: UserResponse) {
        mBinding.user = data
        itemView.setOnClickListener { listener.onClicked(adapterPosition, data) }
    }

}