package com.oskr19.easyshop.screens.categories.domain

import com.oskr19.easyshop.screens.common.dto.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryInfo(categoryId: String): Flow<Category>
}
