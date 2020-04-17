package com.mvvm.coroutines.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.coroutines.base.BaseEvent
import com.mvvm.coroutines.base.BaseViewModel
import com.mvvm.coroutines.common.NetworkCode.ApiFailed
import com.mvvm.coroutines.common.NetworkCode.NoInternet
import com.mvvm.coroutines.common.Snippet
import com.mvvm.coroutines.data.model.MailResponse
import com.mvvm.coroutines.data.model.MenuResponse
import com.mvvm.coroutines.data.model.UserResponse
import com.mvvm.coroutines.data.remote.NetworkResult
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

internal class DashboardViewModel(app: Application) : BaseViewModel(app) {

    private val mRepo: DashboardRepo by instance()
    private val mSnippet: Snippet by instance()

    private val _network = MutableLiveData<BaseEvent<String>>()
    val network = _network as LiveData<BaseEvent<String>>

    private val _user = MutableLiveData<List<UserResponse>>()
    val user = _user as LiveData<List<UserResponse>>

    private val _menu = MutableLiveData<List<MenuResponse>>()
    val menu = _menu as LiveData<List<MenuResponse>>

    private val _mail = MutableLiveData<List<MailResponse>>()
    val mail = _mail as LiveData<List<MailResponse>>

    fun calContactsApi() {
        if (mSnippet.isInternetAvailable()) viewModelScope.launch {
            mRepo.calGetContacts()
                .also(fun(result: NetworkResult<List<UserResponse>>) = when (result) {
                    is NetworkResult.Successful -> _user.value = result.value
                    is NetworkResult.Error -> _network.value = BaseEvent(ApiFailed)
                    is NetworkResult.NoNetwork -> _network.value = BaseEvent(NoInternet)
                    else -> _network.value = BaseEvent(ApiFailed)
                })
        } else _network.value = BaseEvent(NoInternet)
    }

    fun calMenuApi() {
        if (mSnippet.isInternetAvailable()) viewModelScope.launch {
            mRepo.calGetMenu().also(fun(result: NetworkResult<List<MenuResponse>>) = when (result) {
                is NetworkResult.Successful -> _menu.value = result.value
                is NetworkResult.Error -> _network.value = BaseEvent(ApiFailed)
                is NetworkResult.NoNetwork -> _network.value = BaseEvent(NoInternet)
                else -> _network.value = BaseEvent(ApiFailed)
            })
        } else _network.value = BaseEvent(NoInternet)
    }

    fun calMailApi() {
        if (mSnippet.isInternetAvailable()) viewModelScope.launch {
            mRepo.calGetMail().also(fun(result: NetworkResult<List<MailResponse>>) = when (result) {
                is NetworkResult.Successful -> _mail.value = result.value
                is NetworkResult.Error -> _network.value = BaseEvent(ApiFailed)
                is NetworkResult.NoNetwork -> _network.value = BaseEvent(NoInternet)
                else -> _network.value = BaseEvent(ApiFailed)
            })
        } else _network.value = BaseEvent(NoInternet)
    }
}