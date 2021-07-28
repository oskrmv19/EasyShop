package com.oskr19.easyshop.screens.search.data.remote

import com.oskr19.easyshop.screens.common.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by oscar.vergara on 25/07/2021
 */
interface SearchAPI {

    @JvmSuppressWildcards
    @GET("sites/{site_id}/search")
    suspend fun search(@Path("site_id") siteId: String, @QueryMap params: Map<String, Any>): Response<SearchResponse>
}