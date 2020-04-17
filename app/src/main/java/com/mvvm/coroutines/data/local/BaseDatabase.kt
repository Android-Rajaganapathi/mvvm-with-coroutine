package com.mvvm.coroutines.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvvm.coroutines.data.local.converter.DateConverter
import com.mvvm.coroutines.data.local.dao.UserDao
import com.mvvm.coroutines.data.local.entity.User

@Database(
    entities = [
        User::class
    ],
    exportSchema = false,
    views = [
    ],
    version = 17
)

@TypeConverters(DateConverter::class)
abstract class BaseDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var instance: BaseDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(BaseDatabase::class) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context.applicationContext,
                BaseDatabase::class.java,
                "dataBasePath"
            ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}