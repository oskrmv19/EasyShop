package com.oskr19.easyshop.core.domain

/**
 * Created by oscar.vergara on 24/07/2021
 */
object Constants {
    const val TIME_OUT = 10

    const val ATTR_ITEM_CONDITION = "ITEM_CONDITION"

    object ErrorCodes {
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
    }

    object ErrorMessages {
        const val MSG_NOT_FOUND = "Service/Resource not found!"
        const val MSG_INTERNAL_SERVER_ERROR = "Internal server error!"
        const val MSG_CONNECTION_ERROR = "Connection error!"
    }
}