package com.mvvm.coroutines.dashboard

import com.mvvm.coroutines.data.cache.BasePreference
import com.mvvm.coroutines.data.remote.ApiService
import com.mvvm.coroutines.data.remote.executeResult

class DashboardRepo(
    private val mService: ApiService,
    private val mPreference: BasePreference
) {

    suspend fun calGetContacts() =
        mService.getContacts().executeResult()

    suspend fun calGetMenu() =
        mService.getMenu().executeResult()

    suspend fun calGetMail() =
        mService.getMail().executeResult()

}