package com.oskr19.easyshop.screens.detail.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import com.oskr19.easyshop.screens.detail.domain.DetailRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 28/07/2021
 */
class GetDetailProductUseCase(private val repository: DetailRepository) :
    BaseUseCase<DetailResponse, GetDetailProductUseCase.Params>() {

    data class Params(val id: String)

    override suspend fun run(params: Params): Flow<DetailResponse> {
        return repository.getDetail(params.id)
    }
}
