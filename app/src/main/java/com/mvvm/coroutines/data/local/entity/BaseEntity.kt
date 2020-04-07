package com.mvvm.coroutines.data.local.entity

import androidx.room.PrimaryKey

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var creationAt: Long? = System.currentTimeMillis()
}