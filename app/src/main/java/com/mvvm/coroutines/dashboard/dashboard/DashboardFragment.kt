package com.mvvm.coroutines.dashboard.dashboard

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseFragment
import com.mvvm.coroutines.dashboard.DashboardViewModel
import com.mvvm.coroutines.data.model.MenuResponse
import com.mvvm.coroutines.databinding.FragmentDashboardBinding
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_dashboard.*

internal class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(
    R.layout.fragment_dashboard,
    null
) {

    private val _mViewModel: DashboardViewModel by activityViewModels()

    override fun onceCreated() {
        mBinding.mViewModel = _mViewModel

        rvContacts.setHasFixedSize(true)
        rvContacts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        _mViewModel.menu.observe(this, Observer {
            for (item in it) longLog(
                Moshi.Builder().build().adapter(MenuResponse::class.java).toJson(item)
            )
            rvContacts.adapter = DashboardAdapter(it)
        })
    }

    private fun longLog(str: String?) {
        if (str!!.length > 4000) {
            Log.d("$tag :: RRR : ", str.substring(0, 4000))
            longLog(str.substring(4000))
        } else Log.d("$tag :: RRR : ", str)
    }
}