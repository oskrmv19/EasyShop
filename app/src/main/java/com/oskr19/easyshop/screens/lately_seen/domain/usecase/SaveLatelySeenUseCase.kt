package com.oskr19.easyshop.screens.lately_seen.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.lately_seen.domain.repository.LatelySeenRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class SaveLatelySeenUseCase(private val repository: LatelySeenRepository) :
    BaseUseCase<String, SaveLatelySeenUseCase.Params>() {

    data class Params(val productId: String)

    override suspend fun run(params: Params): Flow<String> {
        return repository.saveLatelySeen(params.productId)
    }
}