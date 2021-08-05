package com.oskr19.easyshop.screens.search.domain.usecase

import com.oskr19.easyshop.core.domain.usecase.BaseUseCase
import com.oskr19.easyshop.screens.common.dto.SearchResponse
import com.oskr19.easyshop.screens.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by oscar.vergara on 25/07/2021
 */
class SearchProductUseCase(private val repository: SearchRepository) :
    BaseUseCase<SearchResponse, SearchProductUseCase.Params>() {

    data class Params(
        var offset: Int = 0,
        var query: String?,
        var category: String?,
        var sellerId: String?,
    )

    override suspend fun run(params: Params): Flow<SearchResponse> {
        return repository.searchProduct(params.offset, params.query, params.category,params.sellerId)
    }
}