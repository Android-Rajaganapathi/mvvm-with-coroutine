package com.mvvm.coroutines.di

import com.mvvm.coroutines.base.KodeinViewModelFactory
import com.mvvm.coroutines.common.KodeinModule
import com.mvvm.coroutines.common.PermissionUtils
import com.mvvm.coroutines.common.Snippet
import com.mvvm.coroutines.common.Utils
import com.mvvm.coroutines.dashboard.DashboardRepo
import com.mvvm.coroutines.dashboard.DashboardViewModel
import com.mvvm.coroutines.data.cache.BasePreference
import com.mvvm.coroutines.data.local.BaseDatabase
import com.mvvm.coroutines.login.LoginRepo
import com.mvvm.coroutines.login.LoginViewModel
import com.mvvm.coroutines.register.RegisterRepo
import com.mvvm.coroutines.register.RegisterViewModel
import com.mvvm.coroutines.splash.SplashRepo
import com.mvvm.coroutines.splash.SplashViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val appModule = Kodein.Module(KodeinModule.AppModule, false) {
    bind() from singleton { KodeinViewModelFactory(kodein) }
    bind() from singleton { Snippet(instance()) }
    bind() from singleton { Utils() }
    bind() from singleton { BasePreference(instance()) }
    bind() from singleton { PermissionUtils() }
}

val networkModule = Kodein.Module(KodeinModule.NetworkModule, false) {
    bind() from singleton { NetworkInterceptor(instance()) }
    bind() from singleton { getHttpClient(instance()) }
    bind() from singleton { getRetrofitBuilder(instance()) }
    bind() from singleton { getApiService(instance()) }
}

val databaseModule = Kodein.Module(KodeinModule.DatabaseModule, false) {
    bind() from singleton { BaseDatabase(instance()) }
    bind() from singleton { instance<BaseDatabase>().userDao() }
}

val repositoryModule = Kodein.Module(KodeinModule.RepositoryModule, false) {
    bind() from provider { SplashRepo() }
    bind() from provider { LoginRepo(instance()) }
    bind() from provider { RegisterRepo(instance()) }
    bind() from provider { DashboardRepo(instance(), instance()) }
}

val viewModelModule = Kodein.Module(KodeinModule.ViewModelModule, false) {
    bind() from provider { SplashViewModel(instance()) }
    bind() from provider { LoginViewModel(instance()) }
    bind() from provider { RegisterViewModel(instance()) }
    bind() from provider { DashboardViewModel(instance()) }
}