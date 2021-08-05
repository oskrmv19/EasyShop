package com.oskr19.easyshop.core.domain.failure

/**
 * Created by oscar.vergara on 24/07/2021
 */
sealed class Failure: Throwable() {

    class GenericError(message: String? = null) : Failure()

    class ServerError(message: String? = null) : Failure()

    object NoConnection : Failure()

    abstract class ServiceFailure: Failure()
}