package com.oskr19.easyshop.screens.favorite.domain.repository

import com.oskr19.easyshop.screens.common.dto.ProductSearch
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
interface FavoriteRepository {
    suspend fun setFavorite(productId: String, isFavorite: Boolean): Flow<String>
    suspend fun getAllFavorites(): Flow<List<ProductSearch>>
    suspend fun removeAll(): Flow<Boolean>
}
