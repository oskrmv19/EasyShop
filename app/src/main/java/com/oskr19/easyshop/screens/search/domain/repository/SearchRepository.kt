package com.oskr19.easyshop.screens.search.domain.repository

import com.oskr19.easyshop.screens.common.dto.SearchResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 25/07/2021
 */
interface SearchRepository {
    suspend fun searchProduct(offset: Int, query: String?, category: String?, sellerId: String?): Flow<SearchResponse>
}