package com.oskr19.easyshop.screens.categories.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.categories.domain.CategoryRepository
import com.oskr19.easyshop.screens.common.dto.Category
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 28/07/2021
 */
class GetCategoriesUseCase(private val repository: CategoryRepository) :
    BaseUseCase<List<Category>, GetCategoriesUseCase.Params>() {

    data class Params(val any: Any)

    override suspend fun run(params: Params): Flow<List<Category>> {
        return repository.getCategories()
    }
}
