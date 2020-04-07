package com.mvvm.coroutines.common.fab

import android.content.Context
import android.os.Build
import kotlin.math.roundToInt

internal object Util {
    @JvmStatic
    fun dpToPx(context: Context, dp: Float) =
        (dp * context.resources.displayMetrics.density).roundToInt()

    @JvmStatic
    fun hasLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}