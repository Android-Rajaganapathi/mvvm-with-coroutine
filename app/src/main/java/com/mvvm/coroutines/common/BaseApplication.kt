package com.mvvm.coroutines.common

import android.app.Application
import com.mvvm.coroutines.di.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class BaseApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@BaseApplication))
        import(appModule)
        import(networkModule)
        import(databaseModule)
        import(viewModelModule)
        import(repositoryModule)
    }
}