package com.oskr19.easyshop.screens.lately_seen.domain.repository

import com.oskr19.easyshop.screens.common.dto.ProductSearch
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
interface LatelySeenRepository {
    suspend fun saveLatelySeen(productId: String): Flow<String>
    suspend fun getAll(): Flow<List<ProductSearch>>
}
