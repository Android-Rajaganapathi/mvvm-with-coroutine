package com.mvvm.coroutines.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mvvm.coroutines.data.local.BaseDatabase
import com.mvvm.coroutines.data.remote.ApiService
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class TransactionWorkManager(private val ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params), KodeinAware {

    override val kodein by closestKodein(ctx)
    override val kodeinContext = kcontext(ctx)

    private val mDatabase: BaseDatabase by instance()
    private val mService: ApiService by instance()

    override suspend fun doWork() = try {
        postDataToServer()
        println("RRR :: TransactionWorkManager.doWork")
        Result.success()
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        Result.retry()
    }

    private suspend fun postDataToServer() {
//        val mMasterRequest = MasterRequest()
//        mMasterRequest.order = mDatabase.orderDao().getAll()
//
//        if (mMasterRequest.order.isNotEmpty() && Snippet(ctx).isInternetAvailable())
//            coroutineScope {
//                try {
//                    calTransactionAPI(mMasterRequest)
//                        .also(fun(result: NetworkResult<BaseResponse>) {
//                            println("RRR :: result = $result")
//                            when (result) {
//                                is NetworkResult.Successful -> if (result.value.status == "true") {
//
//                                } else {
//
//                                }
//                                is NetworkResult.Error -> {
//                                }
//                                is NetworkResult.NoNetwork -> {
//                                }
//                                else -> {
//                                }
//                            }
//                        })
//                } catch (e: ConnectException) {
//                    e.printStackTrace()
//                }
//            }
    }

    private fun longLog(str: String?, tag: String?) {
        if (str!!.length > 4000) {
            Log.d("$tag :: RRR : ", str.substring(0, 4000))
            longLog(str.substring(4000), tag)
        } else Log.d("$tag :: RRR : ", str)
    }

//    private suspend fun calTransactionAPI(request: MasterRequest) =
//        mService.postTransactionNew(request).executeResult()
}