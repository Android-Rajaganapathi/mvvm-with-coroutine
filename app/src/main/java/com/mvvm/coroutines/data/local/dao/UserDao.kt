package com.mvvm.coroutines.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.mvvm.coroutines.data.local.entity.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("select * from User")
    fun getAll(): List<User>

    @Query("DELETE FROM User")
    fun deleteAll()

    @Query("select * from User where name = :name and password = :pass")
    fun getData(name: String, pass: String): List<User>
}