package com.oskr19.easyshop.screens.detail.data

import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import com.oskr19.easyshop.screens.detail.domain.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by oscar.vergara on 28/07/2021
 */
class DetailRepositoryImpl(
    networkHandler: NetworkHandler,
    private val api: DetailAPI
) : BaseRepository(networkHandler), DetailRepository {

    override suspend fun getDetail(id: String): Flow<DetailResponse> {
        return flow {
            try {
                val response = api.detail(id)
                with(response){
                    if (isSuccessful){
                        body()?.let { body->
                            emit(body)
                        }
                    } else {
                        throw Failure.ServerError(message = response.message())
                    }
                }
            } catch (e: Exception){
                throw resolveFailure(e)
            }
        }
    }

    override suspend fun getDescription(id: String): Flow<String> {
        return flow {
            try {
                val response = api.description(id)
                with(response){
                    if (isSuccessful){
                        body()?.let { body->
                            emit(body.plainText)
                        }
                    } else {
                        throw Failure.ServerError(message = response.message())
                    }
                }
            } catch (e: Exception){
                throw resolveFailure(e)
            }
        }
    }
}