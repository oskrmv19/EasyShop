package com.oskr19.easyshop.screens.search.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.common.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.search.domain.usecase.SearchProductUseCase
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import com.oskr19.easyshop.screens.search.presentation.model.SearchParams
import com.oskr19.easyshop.screens.search.presentation.state.LoadingProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by oscar.vergara on 25/07/2021
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val searchUseCase: SearchProductUseCase,
    private val mapper: ProductUIMapper
) : BaseViewModel(application) {

    private val _result = MutableLiveData<List<ProductUI>?>()
    val results: LiveData<List<ProductUI>?> get() = _result

    private val _searchParams = MutableLiveData<SearchParams>()
    val searchParams: LiveData<SearchParams> get() = _searchParams

    private val _products = arrayListOf<ProductSearch>()
    private lateinit var _selected: ProductSearch
    private val params = SearchParams()

    fun search() {
        launch {
            searchUseCase.run( SearchProductUseCase.Params( params.offset, params.query, params.category, params.sellerId ) )
                .onStart { if (params.offset == 0) setEventLoading() else setCustomEvent(LoadingProductsState()) }
                .onEmpty { setEventError(Failure.ServerError()) }
                .catch { handleFailure(it) }
                .collect {
                    params.setOffset(it.paging.offset + it.results.size)
                          .setLastPage(it.paging.offset + it.paging.limit > it.paging.total)

                    _searchParams.postValue(params)
                    _result.postValue(mapper.mapFromList(it.results))
                    _products.addAll(it.results)
                    setEventFinished()
                }
        }
    }

    fun setParams(query: String?, category: String?, sellerId: String?) {
        _products.clear()
        _result.postValue(listOf())
        params
            .setQuery(query)
            .setCategory(category)
            .setSellerId(sellerId)
            .resetOffset()
            .setLastPage(false)
    }

    fun setSelectedPosition(position: Int){
        _selected = _products[position]
    }

    fun setSelectedProduct(productSearch: ProductSearch){
        _selected = productSearch
    }

    fun getSelectedProduct() = _selected

}