package com.mvvm.coroutines.dashboard

import android.content.DialogInterface
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.ApiFailureDialogListener
import com.mvvm.coroutines.base.BaseActivity
import com.mvvm.coroutines.common.NetworkCode
import com.mvvm.coroutines.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*

internal class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>(
    R.layout.activity_dashboard,
    DashboardViewModel::class.java
) {

    override fun onceCreated() {
        mBinding.mViewModel = mViewModel

        val navController = findNavController(R.id.navHostFragment)

        setupActionBarWithNavController(
            navController, AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.nav_dashboard, R.id.nav_notifications
                )
            )
        )

        navView.setupWithNavController(navController)

        mViewModel.network.observe(this, Observer {
            it.getContent()?.let { code ->
                when (code) {
                    NetworkCode.NoInternet -> apiFailureDialog(object : ApiFailureDialogListener {
                        override fun onRetry(dialog: DialogInterface, which: Int) {
                            dialog.dismiss()
                            mViewModel.calMenuApi()
                        }
                    })
                    NetworkCode.ApiFailed -> showToast(R.string.something_went_wrong, false)
                    else -> showToast(code)
                }
            }
        })
    }

}