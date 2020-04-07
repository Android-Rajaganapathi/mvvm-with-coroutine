package com.mvvm.coroutines.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mvvm.coroutines.data.cache.PreferenceObject.message

class BasePreference(context: Context) {

    private var preferencesInstance: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    val preferences: SharedPreferences
        get() = preferencesInstance

    inline fun <reified T> put(key: String, value: T) {
        val editor = preferences.edit()
        when (T::class) {
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            Float::class -> editor.putFloat(key, value as Float)
            Int::class -> editor.putInt(key, value as Int)
            Long::class -> editor.putLong(key, value as Long)
            String::class -> editor.putString(key, value as String)
            Set::class -> editor.putStringSet(key, value as Set<String>)
            else -> throw UnsupportedOperationException(message)
        }
        editor.apply()
    }

    inline fun <reified T> get(key: String, defValue: T? = null) = when (T::class) {
        Boolean::class -> preferences.getBoolean(key, defValue as? Boolean ?: false) as T?
        Float::class -> preferences.getFloat(key, defValue as? Float ?: -1f) as T?
        Int::class -> preferences.getInt(key, defValue as? Int ?: -1) as T?
        Long::class -> preferences.getLong(key, defValue as? Long ?: -1L) as T?
        String::class -> preferences.getString(key, defValue as? String) as T?
        Set::class -> preferences.getStringSet(key, defValue as? Set<String>) as T?
        else -> throw UnsupportedOperationException(message)
    }

    fun removeAll() = preferences.edit().clear().apply()

    fun remove(key: String) = preferences.edit().remove(key).apply()

}