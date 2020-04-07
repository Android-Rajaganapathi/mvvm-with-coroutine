package com.mvvm.coroutines.common

import androidx.work.ListenableWorker

object KodeinModule {
    const val AppModule = "AppModule"
    const val NetworkModule = "NetworkModule"
    const val DatabaseModule = "DatabaseModule"
    const val ViewModelModule = "ViewModelModule"
    const val RepositoryModule = "RepositoryModule"
}

object OrderStatus {
    const val Draft = "Draft"
    const val Ordered = "Entered"
    const val Cancelled = "Cancelled"
    const val Template = "Template"
}

object NetworkCode {
    const val NoInternet = "NoInternet"
    const val ApiFailed = "ApiFailed"
    const val ResponsePositive = "ResponsePositive"
}

const val UiDateFormat = "dd MMM yy"
const val DbDateFormat = "ddMMhhmmss"
const val ApiDateFormat = "yyyy-MM-dd"
const val TimeFormat = "HH:mm"

object EventTag {
    const val TagName = "TagName"
    const val TagPassword = "TagPassword"
    const val TagNoUser = "TagNoUser"
    const val Failure = "Failure"
    const val Success = "Success"
}

object Bundle {
    const val BundleOrderId = "BundleOrderId"
    const val BundleOrderNumber = "BundleOrderNumber"
}