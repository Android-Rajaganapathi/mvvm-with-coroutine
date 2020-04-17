package com.mvvm.coroutines.login

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.coroutines.BuildConfig
import com.mvvm.coroutines.base.BaseEvent
import com.mvvm.coroutines.base.BaseViewModel
import com.mvvm.coroutines.common.EventTag
import com.mvvm.coroutines.common.EventTag.Failure
import com.mvvm.coroutines.common.EventTag.Success
import com.mvvm.coroutines.common.EventTag.TagNoUser
import org.kodein.di.generic.instance

internal class LoginViewModel(app: Application) : BaseViewModel(app) {

    private val mRepo: LoginRepo by instance()

    val name = MutableLiveData("")
    val password = MutableLiveData("")

//    init {
//        if (BuildConfig.DEBUG) {
//            name.value = "aaa"
//            password.value = "aaa"
//        }
//    }

    private val _validation = MutableLiveData<BaseEvent<String>>()
    val validation = _validation as LiveData<BaseEvent<String>>

    fun onLoginClicked(view: View) {
        when {
            name.value.isNullOrEmpty() -> _validation.value = BaseEvent(EventTag.TagName)
            password.value.isNullOrEmpty() -> _validation.value = BaseEvent(EventTag.TagPassword)
            mRepo.getAllUsers().isEmpty() -> _validation.value = BaseEvent(TagNoUser)
            else -> {
                println("RRR :: name = ${name.value} password = ${password.value}")
                val user = mRepo.getUser(name.value!!, password.value!!)
                println("RRR :: user = $user")

                if (user.size == 1) _validation.value = BaseEvent(Success)
                else _validation.value = BaseEvent(Failure)
            }
        }
    }
}