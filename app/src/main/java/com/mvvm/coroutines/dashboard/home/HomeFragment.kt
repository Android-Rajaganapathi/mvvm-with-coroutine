package com.mvvm.coroutines.dashboard.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.coroutines.R
import com.mvvm.coroutines.base.BaseFragment
import com.mvvm.coroutines.dashboard.DashboardViewModel
import com.mvvm.coroutines.data.model.UserResponse
import com.mvvm.coroutines.databinding.FragmentHomeBinding
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_home.*

internal class HomeFragment : BaseFragment<FragmentHomeBinding, DashboardViewModel>(
    R.layout.fragment_home,
    null
) {

    private val _mViewModel: DashboardViewModel by activityViewModels()

    override fun onceCreated() {
        mBinding.mViewModel = _mViewModel

        rvUsers.setHasFixedSize(true)
        rvUsers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        _mViewModel.user.observe(this, Observer {
            for (item in it) longLog(
                Moshi.Builder().build().adapter(UserResponse::class.java).toJson(item)
            )
            rvUsers.adapter = HomeAdapter(it)
        })
    }

    private fun longLog(str: String?) {
        if (str!!.length > 4000) {
            Log.d("$tag :: RRR : ", str.substring(0, 4000))
            longLog(str.substring(4000))
        } else Log.d("$tag :: RRR : ", str)
    }
}