package com.mvvm.coroutines.dashboard.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.coroutines.R
import com.mvvm.coroutines.data.model.MailResponse
import com.mvvm.coroutines.databinding.ItemMailBinding

class NotificationAdapter(private val mItems: List<MailResponse>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) =
        NotificationViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_mail, viewGroup, false
            )
        )

    override fun onBindViewHolder(holder: NotificationViewHolder, i: Int) {
        holder.mBinding.mail = mItems[i]
    }

    override fun getItemCount() = mItems.size

    inner class NotificationViewHolder(val mBinding: ItemMailBinding) :
        RecyclerView.ViewHolder(mBinding.root)
}