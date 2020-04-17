package com.mvvm.coroutines.data.remote

import com.mvvm.coroutines.data.model.MailResponse
import com.mvvm.coroutines.data.model.MenuResponse
import com.mvvm.coroutines.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    //    https://api.androidhive.info/json/contacts.json
    @GET("json/contacts.json")
    suspend fun getContacts(): Response<List<UserResponse>>

    //    https://api.androidhive.info/json/shimmer/menu.php
    @GET("json/shimmer/menu.php")
    suspend fun getMenu(): Response<List<MenuResponse>>

    //    https://api.androidhive.info/json/inbox.json
    @GET("json/inbox.json")
    suspend fun getMail(): Response<List<MailResponse>>

}