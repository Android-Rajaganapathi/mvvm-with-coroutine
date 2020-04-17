package com.mvvm.coroutines.dashboard.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.coroutines.R
import com.mvvm.coroutines.data.model.MenuResponse
import com.mvvm.coroutines.databinding.ItemMenuBinding

class DashboardAdapter(private val mItems: List<MenuResponse>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) =
        DashboardViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_menu, viewGroup, false
            )
        )

    override fun onBindViewHolder(holder: DashboardViewHolder, i: Int) {
        holder.mBinding.menu = mItems[i]
    }

    override fun getItemCount() = mItems.size

    class DashboardViewHolder(val mBinding: ItemMenuBinding) :
        RecyclerView.ViewHolder(mBinding.root)
}