package com.oskr19.easyshop.core.data.repository

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
open class BaseRepository(val networkHandler: NetworkHandler) {
    lateinit var error: Failure

    fun resolveFailure(e: Exception): Failure {
        Timber.e(e, "Repository Error")
        error = when (e) {
            is SocketTimeoutException -> {
                Failure.ServerError("connection error!")
            }
            is ConnectException -> {
                Failure.NoConnection
            }
            is UnknownHostException -> {
                Failure.NoConnection
            }
            else -> {
                validateByCode(e)
            }
        }

        return error
    }

    private fun validateByCode(e: Exception): Failure {
        return if(e is HttpException){
            when(e.code()){
                500 -> {
                    Failure.ServerError("internal error!")
                }
                404 -> {
                    Failure.ServerError("resource not found!")
                }
                else -> {
                    Failure.ServerError(e.message)
                }
            }
        } else {
            Failure.ServerError()
        }
    }
}