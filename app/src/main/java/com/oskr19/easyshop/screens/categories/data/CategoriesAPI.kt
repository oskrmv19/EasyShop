package com.oskr19.easyshop.screens.categories.data

import com.oskr19.easyshop.screens.common.dto.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by oscar.vergara on 28/07/2021
 */
interface CategoriesAPI {

    @GET("sites/{site_id}/categories")
    suspend fun getCategories(@Path("site_id") siteId: String): Response<List<Category>>

    @GET("categories/{category_id}")
    suspend fun getCategoryInfo(@Path("category_id") categoryId: String): Response<Category>


}