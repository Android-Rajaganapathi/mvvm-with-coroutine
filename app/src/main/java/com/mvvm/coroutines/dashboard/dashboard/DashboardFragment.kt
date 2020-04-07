package com.mvvm.coroutines.dashboard.dashboard

import androidx.fragment.app.activityViewModels
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseFragment
import com.mvvm.coroutines.dashboard.DashboardViewModel
import com.mvvm.coroutines.databinding.FragmentHomeBinding

internal class DashboardFragment : BaseFragment<FragmentHomeBinding, DashboardViewModel>(
    R.layout.fragment_dashboard,
    null
) {

    private val _mViewModel: DashboardViewModel by activityViewModels()

    override fun onceCreated() {
        mBinding.mViewModel = _mViewModel
    }
}