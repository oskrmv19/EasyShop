package com.oskr19.easyshop.screens.categories.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.categories.domain.CategoryRepository
import com.oskr19.easyshop.screens.common.dto.Category
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 28/07/2021
 */
class GetCategoryInfoUseCase(private val repository: CategoryRepository) :
    BaseUseCase<Category, GetCategoryInfoUseCase.Params>() {

    data class Params(val categoryId: String)

    override suspend fun run(params: Params): Flow<Category> {
        return repository.getCategoryInfo(params.categoryId)
    }
}