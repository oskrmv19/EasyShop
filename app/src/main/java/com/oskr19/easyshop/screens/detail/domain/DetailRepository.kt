package com.oskr19.easyshop.screens.detail.domain

import com.oskr19.easyshop.screens.common.dto.DetailResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 28/07/2021
 */
interface DetailRepository {
    suspend fun getDetail(id: String): Flow<DetailResponse>
    suspend fun getDescription(id: String): Flow<String>
}