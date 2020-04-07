package com.mvvm.coroutines.splash

import android.app.Application
import com.mvvm.coroutines.base.BaseViewModel
import org.kodein.di.generic.instance

internal class SplashViewModel(app: Application) : BaseViewModel(app) {

    private val mRepo: SplashRepo by instance()

}