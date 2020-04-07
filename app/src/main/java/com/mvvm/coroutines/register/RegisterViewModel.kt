package com.mvvm.coroutines.register

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.coroutines.base.BaseEvent
import com.mvvm.coroutines.base.BaseViewModel
import com.mvvm.coroutines.common.EventTag.TagName
import com.mvvm.coroutines.common.EventTag.TagPassword
import com.mvvm.coroutines.data.local.entity.User
import org.kodein.di.generic.instance

internal class RegisterViewModel(app: Application) : BaseViewModel(app) {

    private val mRepo: RegisterRepo by instance()

    val name = MutableLiveData("")
    val password = MutableLiveData("")

    private val _validation = MutableLiveData<BaseEvent<String>>()
    val validation = _validation as LiveData<BaseEvent<String>>

    fun onRegisterClicked(view: View) {
        println("RRR :: name = ${name.value} password = ${password.value}")
        when {
            name.value.isNullOrEmpty() -> _validation.value = BaseEvent(TagName)
            password.value.isNullOrEmpty() -> _validation.value = BaseEvent(TagPassword)
            else -> {
                mRepo.insertUser(User(name.value, password.value))
                name.value = ""
                password.value = ""
            }
        }
    }
}