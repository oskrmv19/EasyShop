package com.oskr19.easyshop.screens.search.data.remote

import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.common.dto.SearchResponse
import com.oskr19.easyshop.screens.search.domain.repository.SearchRepository
import com.oskr19.easyshop.screens.search.domain.request.SearchRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by oscar.vergara on 25/07/2021
 */
class SearchRepositoryImpl(
    networkHandler: NetworkHandler,
    prefs: EasyShopPrefs,
    private val api: SearchAPI
    ) : BaseRepository(networkHandler, prefs), SearchRepository {

    override suspend fun searchProduct(query: String): Flow<SearchResponse> {
        return flow {
            try {
                val params = SearchRequest.buildSearchParams(query)
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
        }
    }
}