package com.oskr19.easyshop.screens.detail.data

import com.oskr19.easyshop.screens.common.dto.DescriptionResponse
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by oscar.vergara on 28/07/2021
 */
interface DetailAPI {
    @JvmSuppressWildcards
    @GET("items/{item_id}")
    suspend fun detail(@Path("item_id") siteId: String): Response<DetailResponse>

    @JvmSuppressWildcards
    @GET("items/{item_id}/description")
    suspend fun description(@Path("item_id") siteId: String): Response<DescriptionResponse>
}
