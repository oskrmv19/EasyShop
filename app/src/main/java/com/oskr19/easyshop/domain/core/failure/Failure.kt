package com.oskr19.easyshop.domain.core.failure

/**
 * Created by oscar.vergara on 24/07/2021
 */
sealed class Failure {

    class ServerError(message: String?) : Failure()

    object NoConnection : Failure()

    abstract class ServiceFailure: Failure()
}