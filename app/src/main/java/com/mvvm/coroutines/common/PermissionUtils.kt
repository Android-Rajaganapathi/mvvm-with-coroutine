package com.mvvm.coroutines.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class PermissionUtils {
    fun useRunTimePermissions() = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    fun hasPermission(activity: Activity, permission: String) = !useRunTimePermissions()
            || activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    private val isMarshmallow: Boolean
        get() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) or (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1)

    fun hasPermission(activity: Activity, permissions: Array<String>): Boolean {
        for (permission in permissions) if (!hasPermission(activity, permission)) return false
        return true
    }

    fun requestPermissions(
        activity: FragmentActivity,
        permission: Array<String>,
        requestCode: Int
    ): Boolean {
        if (useRunTimePermissions()) activity.requestPermissions(permission, requestCode)
        return hasPermission(activity, permission)
    }

    fun requestPermissions(fragment: Fragment, permission: Array<String>, requestCode: Int) {
        if (useRunTimePermissions()) fragment.requestPermissions(permission, requestCode)
    }

    fun shouldShowRational(activity: Activity, permission: String) =
        useRunTimePermissions() && activity.shouldShowRequestPermissionRationale(permission)

    fun goToAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    private fun callPermissionSettings(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        val appCompatActivity = context as AppCompatActivity
        appCompatActivity.startActivityForResult(intent, 300)
    }

    fun checkHasPermission(
        context: AppCompatActivity?,
        permissions: Array<String>?
    ): ArrayList<String> {
        val permissionList = ArrayList<String>()
        if (isMarshmallow && context != null && permissions != null)
            for (permission in permissions)
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) permissionList.add(permission)
        return permissionList
    }

    fun onRequestPermissionsResult(
        permissions: Array<String>,
        results: IntArray?
    ): Array<String> {
        val permissionList = ArrayList<String>()
        if (results != null && results.isNotEmpty())
            for (i in permissions.indices)
                if (results[i] != PackageManager.PERMISSION_GRANTED)
                    permissionList.add(permissions[i])
        return permissionList.toTypedArray()
    }
}

