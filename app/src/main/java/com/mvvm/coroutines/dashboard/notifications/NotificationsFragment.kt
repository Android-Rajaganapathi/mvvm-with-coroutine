package com.mvvm.coroutines.dashboard.notifications

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseFragment
import com.mvvm.coroutines.dashboard.DashboardViewModel
import com.mvvm.coroutines.data.model.MailResponse
import com.mvvm.coroutines.databinding.FragmentNotificationsBinding
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_notifications.*

internal class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding, DashboardViewModel>(
        R.layout.fragment_notifications,
        null
    ) {

    private val _mViewModel: DashboardViewModel by activityViewModels()

    override fun onceCreated() {
        mBinding.mViewModel = _mViewModel

        rvMenu.setHasFixedSize(true)
        rvMenu.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        _mViewModel.mail.observe(this, Observer {
            for (item in it) longLog(
                Moshi.Builder().build().adapter(MailResponse::class.java).toJson(item)
            )
            rvMenu.adapter = NotificationAdapter(it)
        })
    }

    private fun longLog(str: String?) {
        if (str!!.length > 4000) {
            Log.d("$tag :: RRR : ", str.substring(0, 4000))
            longLog(str.substring(4000))
        } else Log.d("$tag :: RRR : ", str)
    }

    override fun onResume() {
        super.onResume()
        _mViewModel.calMailApi()
    }
}