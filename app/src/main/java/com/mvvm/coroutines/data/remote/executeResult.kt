package com.mvvm.coroutines.data.remote

import com.squareup.moshi.Moshi
import retrofit2.Response
import java.net.ConnectException

fun <T : Any> Response<T>.executeResult(): NetworkResult<T> {
    try {
        return if (isSuccessful) {
            val body = body()
            if (body == null) {
                if (errorBody() != null) NetworkResult.Failed
                else NetworkResult.SuccessfulEmpty
            } else NetworkResult.Successful(body)
        } else {
            when (code()) {
                401 -> NetworkResult.Unauthorized
                400, 403, 404, 409 -> {
                    val errorBody = errorBody()
                    if (errorBody == null) NetworkResult.Failed
                    else NetworkResult.Error(
                        Moshi.Builder()
                            .build()
                            .adapter(BaseResponse::class.java)
                            .fromJson(errorBody.string())!!
                    )
                }
                else -> NetworkResult.Failed
            }
        }
    } catch (e: ConnectException) {
        println("RRR :: <top>.executeResult :: ConnectException")
        e.printStackTrace()
        return NetworkResult.NoNetwork
    } catch (e: Exception) {
        println("RRR :: <top>.executeResult :: Exception")
        e.printStackTrace()
        return NetworkResult.Failed
    }
}

sealed class NetworkResult<out T : Any> {
    data class Successful<out T : Any>(val value: T) : NetworkResult<T>()
    data class Malformed<out T : Any>(val value: T) : NetworkResult<T>()
    data class Error<out T : Any>(val value: BaseResponse) : NetworkResult<T>()
    object SuccessfulEmpty : NetworkResult<Nothing>()
    object None : NetworkResult<Nothing>()
    object Failed : NetworkResult<Nothing>()
    object NoNetwork : NetworkResult<Nothing>()
    object Unauthorized : NetworkResult<Nothing>()
}

open class BaseResponse(val message: String? = null, val status: String? = null)