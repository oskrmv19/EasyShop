package com.oskr19.easyshop.screens.detail.data

import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import com.oskr19.easyshop.screens.detail.domain.DetailRepository
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

/**
 * Created by oscar.vergara on 28/07/2021
 */
class DetailRepositoryImpl(
    networkHandler: NetworkHandler,
    private val api: DetailAPI,
    private val dao: FavoriteDao
) : BaseRepository(networkHandler), DetailRepository {

    override suspend fun getDetail(id: String): Flow<DetailResponse> {
        return flow {
            try {
                val response = api.detail(id)
                with(response){
                    if (isSuccessful){
                        body()?.let { body->
                            body.favorite = dao.getById(id) != null
                            emit(body)
                        }
                    } else {
                        throw Failure.ServerError(message = response.message())
                    }
                }
            } catch (e: Exception){
                throw resolveFailure(e)
            }
        }.onEach {
            val entity = dao.getById(it.id)
            it.favorite = entity != null
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