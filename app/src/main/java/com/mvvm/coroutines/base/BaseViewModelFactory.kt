package com.mvvm.coroutines.base

//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.mvvm.coroutines.splash.SplashViewModel
//import org.kodein.di.DKodein
//import org.kodein.di.generic.instance
//
//class BaseViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>) = when {
//        modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
//            SplashViewModel(injector.instance()) as T
//        }
//        else -> throw IllegalArgumentException("Unknown class name")
//    }
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
//            ?: modelClass.newInstance()
//    }
//}
