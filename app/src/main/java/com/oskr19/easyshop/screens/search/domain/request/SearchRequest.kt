package com.oskr19.easyshop.screens.search.domain.request

/**
 * Created by oscar.vergara on 25/07/2021
 */
object SearchRequest {
    fun buildSearchParams(query: String): HashMap<String, Any>{
        val params = HashMap<String,Any>()
        params["q"] = query
        params["limit"] = 10
        return params
    }
}