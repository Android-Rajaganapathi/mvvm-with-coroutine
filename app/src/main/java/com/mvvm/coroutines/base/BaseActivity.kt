package com.mvvm.coroutines.base

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.coroutines.R
import com.mvvm.coroutines.common.UiDateFormat
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layout: Int,
    private val viewModelClass: Class<VM>?
) : AppCompatActivity(), KodeinAware {

    private val parentKodein by kodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

    private val mViewModelFactory: ViewModelProvider.Factory by instance()

    protected lateinit var mBinding: VB

    protected lateinit var mViewModel: VM

    protected abstract fun onceCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layout)
        if (viewModelClass != null) mViewModel =
            ViewModelProvider(this, mViewModelFactory).get(viewModelClass)
        mBinding.lifecycleOwner = this
        onceCreated()
    }

    open fun navigateTo(className: Class<*>, doFinish: Boolean = false) {
        startActivity(Intent(this, className).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        if (doFinish) finish()
    }

    open fun navigateTo(className: Class<*>, bundle: Bundle, doFinish: Boolean = false) {
        startActivity(
            Intent(this, className)
                .putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        ); if (doFinish) finish()
    }

    open fun navigateToWithResult(className: Class<*>, requestCode: Int) =
        startActivityForResult(
            Intent(this, className)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode
        )

    open fun navigateToWithResult(className: Class<*>, requestCode: Int, bundle: Bundle) =
        startActivityForResult(
            Intent(this, className)
                .putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode
        )

    open fun showToast(message: String, isShort: Boolean = true) = Toast.makeText(
        this, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()

    open fun showToast(@StringRes resId: Int, isShort: Boolean = true) = Toast.makeText(
        this, getString(resId), if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()

    open fun showDialogYes(listener: DialogListener, msg: String, title: String) {
        val builder = AlertDialog.Builder(this)
        if (title.length > 1) builder.setTitle(title)
        builder.setMessage(msg)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.alert_ok) { dialog, which ->
            listener.onPositiveSelect(dialog, which)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    open fun apiFailureDialog(listener: ApiFailureDialogListener, msg: String = "") {
        val builder = AlertDialog.Builder(this)
        if (msg.length > 1) builder.setMessage(msg)
        else builder.setMessage(getString(R.string.internet_desc))
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.alert_retry) { dialog, which ->
            listener.onRetry(dialog, which)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    open fun showDialogYesNo(listener: DialogListener, @StringRes msg: Int, title: Int = 0) {
        if (title > 1) showDialogYesNo(listener, getString(msg), getString(title))
        else showDialogYesNo(listener, getString(msg), "")
    }

    open fun showDialogYesNo(listener: DialogListener, msg: String, title: String) {
        val builder = AlertDialog.Builder(this)
        if (title.length > 1) builder.setTitle(title)
        builder.setMessage(msg)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.alert_ok) { dialog, which ->
            listener.onPositiveSelect(dialog, which)
        }
        builder.setNegativeButton(R.string.alert_cancel) { dialog, which ->
            listener.onNegativeSelect(dialog, which)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showDateTimePicker(maxDate: Long, mListener: DatePickerListener) {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(UiDateFormat, Locale.getDefault())
        val dialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                mListener.onDateSelected(sdf.format(cal.time), cal.time.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.maxDate = maxDate
        dialog.show()
    }

    fun showDateRangePicker(minDate: Long, maxDate: Long = 0, mListener: DatePickerListener) {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(UiDateFormat, Locale.getDefault())
        val dialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                mListener.onDateSelected(sdf.format(cal.time), cal.time.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = minDate
        if (maxDate != 0L) dialog.datePicker.maxDate = maxDate
        dialog.show()
    }
}