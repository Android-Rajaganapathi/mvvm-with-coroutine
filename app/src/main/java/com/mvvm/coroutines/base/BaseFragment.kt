package com.mvvm.coroutines.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

abstract class BaseFragment<VB : ViewDataBinding, VM : AndroidViewModel>(
    @LayoutRes private val layout: Int,
    private val viewModelClass: Class<VM>?
) : Fragment(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by Kodein.lazy {
        extend(parentKodein, copy = Copy.All)
    }
//    override val kodein by closestKodein()

    private val mViewModelFactory: ViewModelProvider.Factory by instance()

    protected lateinit var mBinding: VB

    protected lateinit var mViewModel: VM

    protected abstract fun onceCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layout, container, false)
        if (viewModelClass != null) mViewModel =
            ViewModelProvider(this, mViewModelFactory).get(viewModelClass)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onceCreated()
    }

    open fun navigateTo(className: Class<*>, doFinish: Boolean = false) {
        startActivity(
            Intent(
                context,
                className
            ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        ); if (doFinish) requireActivity().finish()
    }

    open fun navigateTo(className: Class<*>, bundle: Bundle, doFinish: Boolean = false) {
        startActivity(
            Intent(
                context,
                className
            ).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        ); if (doFinish) requireActivity().finish()
    }

    open fun navigateToWithResult(className: Class<*>, requestCode: Int) =
        startActivityForResult(
            Intent(
                context,
                className
            ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode
        )

    open fun navigateToWithResult(className: Class<*>, requestCode: Int, bundle: Bundle) =
        startActivityForResult(
            Intent(
                context,
                className
            ).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode
        )

    open fun showToast(message: String, isShort: Boolean = true) = Toast.makeText(
        context, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()

    open fun showToast(@StringRes resId: Int, isShort: Boolean = true) = Toast.makeText(
        context, getString(resId), if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()

    open fun showToasta(@StringRes resId: Int, isShort: Boolean = true) {
        (activity as BaseActivity<*, *>).showToast(resId, isShort)
    }
}