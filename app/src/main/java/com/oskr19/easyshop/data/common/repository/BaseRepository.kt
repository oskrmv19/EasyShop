package com.oskr19.easyshop.data.common.repository

import com.oskr19.easyshop.domain.common.failure.Failure
import com.oskr19.easyshop.domain.common.network.INetworkHandler
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by oscar.vergara on 24/07/2021
 */
class BaseRepository(val networkHandler: INetworkHandler) {
    lateinit var error: Failure

    fun resolveFailure(e: Exception): Failure {
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