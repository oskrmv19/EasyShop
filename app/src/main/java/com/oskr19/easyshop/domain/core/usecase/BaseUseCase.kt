package com.oskr19.easyshop.domain.core.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 24/07/2021
 */
abstract class BaseUseCase<out T, in P> where T : Any {

    abstract suspend fun run(params: P): Flow<T>

    suspend operator fun invoke(
        params: P,
        onResult: (Flow<T>) -> Unit = {}
    ) {
        onResult(run(params))
    }
}