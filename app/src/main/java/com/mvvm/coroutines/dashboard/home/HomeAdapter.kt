package com.mvvm.coroutines.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.coroutines.R
import com.mvvm.coroutines.dashboard.home.HomeAdapter.UserViewHolder
import com.mvvm.coroutines.data.model.UserResponse
import com.mvvm.coroutines.databinding.ItemUserBinding

class HomeAdapter(private val mItems: List<UserResponse>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) =
        UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_user, viewGroup, false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, i: Int) {
        val currentStudent = mItems[i]
        holder.mBinding.user = currentStudent
    }

    override fun getItemCount() = mItems.size

    inner class UserViewHolder(val mBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mBinding.root)
}