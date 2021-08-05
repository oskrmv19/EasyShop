package com.oskr19.easyshop.screens.lately_seen.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.lately_seen.domain.repository.LatelySeenRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class GetLatelySeenUseCase(private val repository: LatelySeenRepository) :
    BaseUseCase<List<ProductSearch>, GetLatelySeenUseCase.Params>() {

    data class Params(val any: Any)

    override suspend fun run(params: Params): Flow<List<ProductSearch>> {
        return repository.getAll()
    }
}