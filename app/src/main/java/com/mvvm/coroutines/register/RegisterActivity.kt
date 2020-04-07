package com.mvvm.coroutines.register

import androidx.lifecycle.Observer
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseActivity
import com.mvvm.coroutines.common.EventTag
import com.mvvm.coroutines.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

internal class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(
    R.layout.activity_register,
    RegisterViewModel::class.java
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
                    else -> showToast(code)
                }
            }
        })

        btClose.setOnClickListener { finish() }
    }
}