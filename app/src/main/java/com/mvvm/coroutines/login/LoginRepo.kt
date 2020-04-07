package com.mvvm.coroutines.login

import com.mvvm.coroutines.data.local.dao.UserDao

class LoginRepo(private val mUserDao: UserDao) {

    fun getUser(name: String, pass: String) = mUserDao.getData(name, pass)

    fun getAllUsers() = mUserDao.getAll()

}