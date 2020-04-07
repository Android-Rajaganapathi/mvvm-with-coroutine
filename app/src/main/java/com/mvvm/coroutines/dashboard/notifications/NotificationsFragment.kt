package com.mvvm.coroutines.dashboard.notifications

import androidx.fragment.app.activityViewModels
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseFragment
import com.mvvm.coroutines.dashboard.DashboardViewModel
import com.mvvm.coroutines.databinding.FragmentHomeBinding

internal class NotificationsFragment : BaseFragment<FragmentHomeBinding, DashboardViewModel>(
    R.layout.fragment_notifications,
    null
) {

    private val _mViewModel: DashboardViewModel by activityViewModels()

    override fun onceCreated() {
        mBinding.mViewModel = _mViewModel
    }
}