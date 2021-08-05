package com.oskr19.easyshop.screens.favorite.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class RemoveAllFavoritesUseCase(private val repository: FavoriteRepository) :
    BaseUseCase<Boolean, RemoveAllFavoritesUseCase.Params>() {

    data class Params(val any: Any)

    override suspend fun run(params: Params): Flow<Boolean> {
        return repository.removeAll()
    }
}