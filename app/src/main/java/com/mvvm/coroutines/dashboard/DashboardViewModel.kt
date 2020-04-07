package com.mvvm.coroutines.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.coroutines.base.BaseViewModel

internal class DashboardViewModel(app: Application) : BaseViewModel(app) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}