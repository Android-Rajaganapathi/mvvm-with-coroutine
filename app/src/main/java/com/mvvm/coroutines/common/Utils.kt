package com.mvvm.coroutines.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun hideKeyboard(activity: Activity?) {
        activity?.let { it ->
            it.currentFocus?.applicationWindowToken?.apply {
                if (it.getSystemService(Context.INPUT_METHOD_SERVICE) is InputMethodManager) {
                    val imm =
                        it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(this, 0)
                }
            }
        }
    }

    fun setViewError(view: View?, error: String) {
        if (view is AppCompatEditText) {
            view.error = error
            view.requestFocus()
        } else if (view is AppCompatTextView) {
            view.error = error
            view.requestFocus()
        }
    }

    fun shakeError(): TranslateAnimation {
        val shake = TranslateAnimation(0f, 10f, 0f, 0f)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7f)
        return shake
    }

    fun getOrderSequence(tabCode: String, customerCode: String, count: Int) =
        "$tabCode${customerCode}_${getTimeStamp()}_${count + 1}"

    fun getSurveySequence(tabCode: String, customerCode: String, count: Int) =
        "$tabCode${customerCode}_${getTimeStamp()}_${count + 1}"

    fun getFeedbackSequence(tabCode: String, customerCode: String, count: Int) =
        "$tabCode${customerCode}_${getTimeStamp()}_${count + 1}"

    fun getUiDate(): String = SimpleDateFormat(UiDateFormat, Locale.getDefault()).format(Date())

    fun getApiDate(): String = SimpleDateFormat(ApiDateFormat, Locale.getDefault()).format(Date())

    fun getApiDate(time: Long): String =
        SimpleDateFormat(ApiDateFormat, Locale.getDefault()).format(time)

    fun getMonthYearDate(date: String?): String? {
        return try {
            SimpleDateFormat(
                UiDateFormat, Locale.getDefault()
            ).format(
                SimpleDateFormat(ApiDateFormat, Locale.getDefault()).parse(date!!)!!
            )
        } catch (e: Exception) {
            e.printStackTrace()
            date
        }
    }

    fun getCurrentTime(): String = SimpleDateFormat(TimeFormat, Locale.getDefault()).format(Date())

    fun getTimeStamp(): String = SimpleDateFormat(DbDateFormat, Locale.getDefault()).format(Date())

    fun getQuantityFormat(quantity: String?): String {
        val quantityDouble: Double =
            if (quantity == null || quantity == "") 0.0 else quantity.toDouble()
        var value: String = DecimalFormat("#,###").format(quantityDouble)
        if (value != "") if (value.startsWith(".")) value = "0$value"
        return value
    }

    fun getPriceFormat(price: String?): String {
        val priceDouble: Double = if (price == null || price == "") 0.0 else price.toDouble()
        var value = DecimalFormat("#,###.00").format(priceDouble)
        if (value != "") if (value.startsWith(".")) value = "0$value"
        return value
    }


//    "yyyy.MM.dd G 'at' HH:mm:ss z"	        2001.07.04 AD at 12:08:56 PDT
//    "EEE, MMM d, ''yy"	                    Wed, Jul 4, '01
//    "h:mm a"	                                12:08 PM
//    "hh 'o''clock' a, zzzz"	                12 o'clock PM, Pacific Daylight Time
//    "K:mm a, z"	                            0:08 PM, PDT
//    "yyyyy.MMMMM.dd GGG hh:mm aaa"	        02001.July.04 AD 12:08 PM
//    "EEE, d MMM yyyy HH:mm:ss Z"	            Wed, 4 Jul 2001 12:08:56 -0700
//    "yyMMddHHmmssZ"	                        010704120856-0700
//    "yyyy-MM-dd'T'HH:mm:ss.SSSZ"	            2001-07-04T12:08:56.235-0700
//    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"	        2001-07-04T12:08:56.235-07:00
}