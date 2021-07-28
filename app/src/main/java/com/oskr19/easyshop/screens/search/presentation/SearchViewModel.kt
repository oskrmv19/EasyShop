package com.oskr19.easyshop.screens.search.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.Product
import com.oskr19.easyshop.screens.common.dto.SearchResponse
import com.oskr19.easyshop.screens.search.domain.usecase.SearchProductUseCase
import com.oskr19.easyshop.screens.search.presentation.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
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
    private val searchProductUseCase: SearchProductUseCase,
    private val mapper: ProductUIMapper
): BaseViewModel(application) {

    private val _searchResponse = MutableLiveData<List<ProductUI>>()
    val searchResponse: LiveData<List<ProductUI>> get() = _searchResponse

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> get() = _search

    fun search(query: String){
        launch {
            _search.postValue(query)
            searchProductUseCase.run(SearchProductUseCase.Params(query))
                .onStart { setLoading(true)  }
                .onEmpty { _failure.postValue(Failure.ServerError(null)) }
                .catch { handleFailure(it as Failure) }
                .collect {
                    _searchResponse.postValue(mapper.mapFromList(it.results))
                }
        }
    }
}