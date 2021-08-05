package com.oskr19.easyshop.screens.lately_seen.data

import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.Constants.MAX_LATELY_SEEN_SIZE_ACCEPTED
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import com.oskr19.easyshop.screens.lately_seen.data.local.LatelySeenDao
import com.oskr19.easyshop.screens.lately_seen.data.local.LatelySeenEntity
import com.oskr19.easyshop.screens.lately_seen.data.remote.LatelySeenAPI
import com.oskr19.easyshop.screens.lately_seen.domain.repository.LatelySeenRepository
import com.oskr19.easyshop.screens.lately_seen.domain.request.LatelySeenRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by oscar.vergara on 1/08/2021
 */
class LatelySeenRepositoryImpl(
    networkHandler: NetworkHandler,
    private val dao: LatelySeenDao,
    private val favoriteDao: FavoriteDao,
    private val api: LatelySeenAPI
) : BaseRepository(networkHandler),
    LatelySeenRepository {
    override suspend fun saveLatelySeen(productId: String): Flow<String> {
        return flow {

            dao.getAll()?.let {
                if (it.size > MAX_LATELY_SEEN_SIZE_ACCEPTED) {
                    it.subList(MAX_LATELY_SEEN_SIZE_ACCEPTED - 1, it.size - 1).forEach { product ->
                        dao.deleteById(product.productId)
                    }
                }
            }

            dao.insert(LatelySeenEntity(productId))

            emit(productId)
        }
    }

    override suspend fun getAll(): Flow<List<ProductSearch>> {
        return flow {
            try {
                val list = arrayListOf<ProductSearch>()
                dao.getAll()?.asReversed()?.map { product ->
                    val params = LatelySeenRequest.buildParams(product.productId)
                    val response = api.getLatelySeen(params)
                    with(response) {
                        if (isSuccessful) {
                            body()?.let { resp ->
                                resp.map { item ->
                                    item.body.favorite = favoriteDao.getById(item.body.id) != null
                                    list.add(item.body)
                                }
                            }
                        } else {
                            throw Failure.ServerError(message = response.message())
                        }
                    }
                }

                emit(list)
            } catch (e: Exception) {
                throw resolveFailure(e)
            }
        }
    }
}