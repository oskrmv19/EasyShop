package com.oskr19.easyshop.screens.favorite.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.favorite.domain.repository.FavoriteRepository
import com.oskr19.easyshop.screens.lately_seen.domain.usecase.SaveLatelySeenUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class SetFavoriteUseCase(private val repository: FavoriteRepository) :
    BaseUseCase<String, SetFavoriteUseCase.Params>() {

    data class Params(val productId: String, val isFavorite: Boolean)

    override suspend fun run(params: Params): Flow<String> {
        return repository.setFavorite(params.productId, params.isFavorite)
    }
}