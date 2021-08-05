package com.oskr19.easyshop.screens.lately_seen.domain.request

/**
 * Created by oscar.vergara on 1/08/2021
 */
object LatelySeenRequest {
    fun buildParams(ids: String?): HashMap<String, Any> {
        val params = HashMap<String, Any>()
        ids?.also {
            params["ids"] = it
        }
        return params
    }
}