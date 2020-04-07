package com.mvvm.coroutines.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseViewModel(app: Application) : AndroidViewModel(app), KodeinAware {
    override val kodein by closestKodein(app)
    override val kodeinContext = kcontext(app)
}