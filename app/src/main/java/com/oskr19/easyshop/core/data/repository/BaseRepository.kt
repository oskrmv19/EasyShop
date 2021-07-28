package com.oskr19.easyshop.core.data.repository

import android.util.Log
import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by oscar.vergara on 24/07/2021
 */
open class BaseRepository(val networkHandler: NetworkHandler, val prefs: EasyShopPrefs) {
    lateinit var error: Failure

    fun resolveFailure(e: Exception): Failure {
        e.printStackTrace()
        Log.e("oscar","oscar",e)
        when (e) {
            is SocketTimeoutException -> {
                error = Failure.ServerError("connection error!")
            }
            is ConnectException -> {
                error = Failure.NoConnection
            }
            is UnknownHostException -> {
                error = Failure.NoConnection
            }
        }

        if(e is HttpException){
            error = when(e.code()){
                500 -> {
                    Failure.ServerError("internal error!")
                }
                404 -> {
                    Failure.ServerError("Service not found!")
                }
                else -> {
                    Failure.ServerError(e.message)
                }
            }
        }

        return error
    }
}