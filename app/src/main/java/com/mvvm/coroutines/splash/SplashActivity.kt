package com.mvvm.coroutines.splash

import android.Manifest
import android.os.Handler
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseActivity
import com.mvvm.coroutines.common.PermissionUtils
import com.mvvm.coroutines.databinding.ActivitySplashBinding
import com.mvvm.coroutines.login.LoginActivity
import org.kodein.di.generic.instance

internal class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(
    R.layout.activity_splash,
    SplashViewModel::class.java
) {

    private val mPermission: PermissionUtils by instance()

    override fun onceCreated() {
        mBinding.mViewModel = mViewModel
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {
        if (mPermission.hasPermission(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        ) {
            Handler().postDelayed({
                navigateTo(LoginActivity::class.java, true)
            }, 5000)
        } else {
            mPermission.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            showToast("Permissions Missing")
        }
    }
}