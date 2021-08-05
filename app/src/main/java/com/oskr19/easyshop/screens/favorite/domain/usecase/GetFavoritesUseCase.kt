package com.oskr19.easyshop.screens.favorite.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class GetFavoritesUseCase(private val repository: FavoriteRepository) :
    BaseUseCase<List<ProductSearch>, GetFavoritesUseCase.Params>() {

    data class Params(val any: Any)

    override suspend fun run(params: Params): Flow<List<ProductSearch>> {
        return repository.getAllFavorites()
    }
}