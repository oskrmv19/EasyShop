package com.oskr19.easyshop.screens.lately_seen.data.remote

import com.oskr19.easyshop.screens.common.dto.MultiSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by oscar.vergara on 1/08/2021
 */
interface LatelySeenAPI {
    @JvmSuppressWildcards
    @GET("items")
    suspend fun getLatelySeen(@QueryMap params: Map<String, Any>): Response<List<MultiSearchResponse>>
}
