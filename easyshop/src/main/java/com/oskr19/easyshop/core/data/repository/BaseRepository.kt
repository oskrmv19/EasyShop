package com.oskr19.easyshop.core.data.repository

import com.oskr19.easyshop.core.domain.Constants
import com.oskr19.easyshop.core.domain.Constants.ErrorMessages.MSG_CONNECTION_ERROR
import com.oskr19.easyshop.core.domain.Constants.ErrorMessages.MSG_INTERNAL_SERVER_ERROR
import com.oskr19.easyshop.core.domain.Constants.ErrorMessages.MSG_NOT_FOUND
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
    private val _tag = "Repository Error"
    lateinit var error: Failure

    fun resolveFailure(e: Exception): Failure {
        Timber.e(e, _tag)
        error = when (e) {
            is SocketTimeoutException -> {
                Failure.ServerError(MSG_CONNECTION_ERROR)
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
                Constants.ErrorCodes.INTERNAL_SERVER_ERROR -> {
                    Failure.ServerError(MSG_INTERNAL_SERVER_ERROR)
                }
                Constants.ErrorCodes.NOT_FOUND -> {
                    Failure.ServerError(MSG_NOT_FOUND)
                }
                else -> {
                    Failure.ServerError(e.message)
                }
            }
        } else {
            Failure.ServerError(e.message)
        }
    }
}
