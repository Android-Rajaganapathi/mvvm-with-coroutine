package com.mvvm.coroutines.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<in T> {
    @Insert
    fun insertAll(objects: List<T>)

    @Insert
    fun insert(obj: T)

    @Update
    fun update(entity: T)

    @Delete
    fun delete(entity: T)
}