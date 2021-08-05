package com.oskr19.easyshop.screens.favorite.data

import android.text.TextUtils
import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteEntity
import com.oskr19.easyshop.screens.favorite.data.remote.FavoriteAPI
import com.oskr19.easyshop.screens.lately_seen.data.remote.LatelySeenAPI
import com.oskr19.easyshop.screens.favorite.domain.repository.FavoriteRepository
import com.oskr19.easyshop.screens.favorite.domain.request.FavoriteRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class FavoriteRepositoryImpl(
    networkHandler: NetworkHandler,
    private val dao: FavoriteDao,
    private val api: FavoriteAPI
) : BaseRepository(networkHandler),
    FavoriteRepository {
    override suspend fun setFavorite(productId: String, isFavorite: Boolean): Flow<String> {
        return flow {
            if (isFavorite) {
                dao.deleteById(productId)
            } else {
                dao.insert(FavoriteEntity(productId))
            }
            emit(productId)
        }
    }

    override suspend fun getAllFavorites(): Flow<List<ProductSearch>> {
        return flow {
            try {
                val ids = dao.getAll()?.joinToString { it.productId }
                if(!TextUtils.isEmpty(ids)) {
                    val params = FavoriteRequest.buildFavoriteParams(ids)
                    val response = api.getFavorites(params)
                    with(response) {
                        if (isSuccessful) {
                            body()?.let { body ->
                                val resp = body.flatMap { item ->
                                    val product = item.body
                                    product.favorite = true
                                    listOf(product)
                                }
                                emit(resp)
                            }
                        } else {
                            throw Failure.ServerError(message = response.message())
                        }
                    }
                }
            } catch (e: Exception) {
                throw resolveFailure(e)
            }
        }
    }

    override suspend fun removeAll(): Flow<Boolean> {
        return flow {
            try {
                dao.deleteAll()
                emit(true)
            } catch (e: Exception) {
                throw resolveFailure(e)
            }
        }
    }
}