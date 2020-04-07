package com.mvvm.coroutines.base

import android.content.DialogInterface

interface DialogListener {

    fun onPositiveSelect(dialog: DialogInterface, which: Int)

    fun onNegativeSelect(dialog: DialogInterface, which: Int)

}

interface ApiFailureDialogListener {

    fun onRetry(dialog: DialogInterface, which: Int)

}
