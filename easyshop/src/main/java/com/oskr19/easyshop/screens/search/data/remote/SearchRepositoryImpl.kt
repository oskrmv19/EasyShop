package com.oskr19.easyshop.screens.search.data.remote

import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.common.dto.SearchResponse
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import com.oskr19.easyshop.screens.search.domain.repository.SearchRepository
import com.oskr19.easyshop.screens.search.domain.request.SearchRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

/**
 * Created by oscar.vergara on 25/07/2021
 */
class SearchRepositoryImpl(
    networkHandler: NetworkHandler,
    private val prefs: EasyShopPrefs,
    private val api: SearchAPI,
    private val dao: FavoriteDao,
    ) : BaseRepository(networkHandler), SearchRepository {

    override suspend fun searchProduct(offset: Int, query: String?, category: String?, sellerId: String?): Flow<SearchResponse> {
        return flow {
            try {
                val params = SearchRequest.buildSearchParams(query, offset,category, sellerId)
                val response = api.search(prefs.getSiteID(),params)
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
        }.onEach {
            it.results.map { product ->
                val entity = dao.getById(product.id)
                product.favorite = entity != null
            }
        }
    }
}
