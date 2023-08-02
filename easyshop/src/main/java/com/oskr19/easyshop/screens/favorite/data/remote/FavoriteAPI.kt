package com.oskr19.easyshop.screens.favorite.data.remote

import com.oskr19.easyshop.screens.common.dto.MultiSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by oscar.vergara on 1/08/2021
 */
interface FavoriteAPI {
    @JvmSuppressWildcards
    @GET("items")
    suspend fun getFavorites(@QueryMap params: Map<String, Any>): Response<List<MultiSearchResponse>>
}
