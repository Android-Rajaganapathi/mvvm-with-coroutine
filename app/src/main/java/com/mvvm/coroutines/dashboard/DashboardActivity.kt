package com.mvvm.coroutines.dashboard

import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseActivity
import com.mvvm.coroutines.databinding.ActivityDashboardBinding

internal class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>(
    R.layout.activity_dashboard,
    DashboardViewModel::class.java
) {

    override fun onceCreated() {
        mBinding.mViewModel = mViewModel
    }

}