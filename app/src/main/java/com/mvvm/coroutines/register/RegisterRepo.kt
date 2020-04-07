package com.mvvm.coroutines.register

import com.mvvm.coroutines.data.local.BaseDatabase
import com.mvvm.coroutines.data.local.entity.User

class RegisterRepo(private val mDatabase: BaseDatabase) {

    fun insertUser(user: User) = mDatabase.userDao().insert(user)

}