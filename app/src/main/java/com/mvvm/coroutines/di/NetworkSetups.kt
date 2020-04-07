package com.mvvm.coroutines.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mvvm.coroutines.BuildConfig.baseUrl
import com.mvvm.coroutines.data.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.ConnectException
import java.util.concurrent.TimeUnit

fun getRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
    Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

fun getHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(networkInterceptor)
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .build()
}

fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(
    ApiService::class.java
)

class NetworkInterceptor(
    context: Context
) : Interceptor {

    private val ctx = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw ConnectException("RRR :: No internet connectivity is available.")
        return chain.proceed(chain.request().newBuilder().build())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) result = true
                else if (type == ConnectivityManager.TYPE_MOBILE) result = true
            }
        }
        return result
    }
}