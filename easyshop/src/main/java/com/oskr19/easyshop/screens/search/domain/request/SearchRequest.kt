package com.oskr19.easyshop.screens.search.domain.request

import com.oskr19.easyshop.core.domain.Constants.DEFAULT_FETCH_LIMIT
import com.oskr19.easyshop.core.domain.Constants.ErrorMessages.NULL_VALUES_MESSAGE

/**
 * Created by oscar.vergara on 25/07/2021
 */
object SearchRequest {
    const val LIMIT = "limit"
    const val OFFSET = "offset"
    const val QUERY = "q"
    const val CATEGORY = "category"
    const val SELLER_ID = "seller_id"

    fun buildSearchParams(query: String?, offset: Int, category: String?, sellerId: String?): HashMap<String, Any>{
        val params = HashMap<String,Any>()

        params[LIMIT] = DEFAULT_FETCH_LIMIT
        params[OFFSET] = offset

        if (query.isNullOrEmpty().and(category.isNullOrEmpty()).and(sellerId.isNullOrEmpty()))
            throw IllegalArgumentException(NULL_VALUES_MESSAGE)

        if(!query.isNullOrEmpty()) {
            params[QUERY] = query
        }
        if (!category.isNullOrEmpty()){
            params[CATEGORY] = category
        }
        if(!sellerId.isNullOrEmpty()){
            params[SELLER_ID] = sellerId
        }

        return params
    }
}
