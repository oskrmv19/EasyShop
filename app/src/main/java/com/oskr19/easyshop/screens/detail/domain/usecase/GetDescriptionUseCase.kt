package com.oskr19.easyshop.screens.detail.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.detail.domain.DetailRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 28/07/2021
 */
class GetDescriptionUseCase(private val repository: DetailRepository) :
    BaseUseCase<String, GetDescriptionUseCase.Params>() {

    data class Params(val id: String)

    override suspend fun run(params: Params): Flow<String> {
        return repository.getDescription(params.id)
    }
}