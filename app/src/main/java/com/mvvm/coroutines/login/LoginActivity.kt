package com.mvvm.coroutines.login

import androidx.lifecycle.Observer
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseActivity
import com.mvvm.coroutines.common.EventTag
import com.mvvm.coroutines.dashboard.DashboardActivity
import com.mvvm.coroutines.databinding.ActivityLoginBinding
import com.mvvm.coroutines.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

internal class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login,
    LoginViewModel::class.java
) {

    override fun onceCreated() {
        mBinding.mViewModel = mViewModel

        mViewModel.validation.observe(this, Observer {
            it.getContent()?.let { code ->
                when (code) {
                    EventTag.TagName -> {
                        tieName.error = getString(R.string.invalid_name)
                        tiePassword.error = null
                    }
                    EventTag.TagPassword -> {
                        tieName.error = null
                        tiePassword.error = getString(R.string.invalid_password)
                    }
                    EventTag.TagNoUser -> showToast(R.string.no_users)
                    EventTag.Failure -> showToast(R.string.invalid_users)
                    EventTag.Success -> navigateTo(DashboardActivity::class.java, true)
                    else -> showToast(code)
                }
            }
        })

        btRegister.setOnClickListener {
            navigateTo(RegisterActivity::class.java)
        }
    }
}