package com.oskr19.easyshop.screens.search.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.common.dto.SearchResponse
import com.oskr19.easyshop.screens.common.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.search.domain.usecase.SearchProductUseCase
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

    private val _result = MutableLiveData<List<ProductUI>>()
    val results: LiveData<List<ProductUI>> get() = _result

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> get() = _search

    private lateinit var _searchResponse: SearchResponse
    private lateinit var _selected: ProductSearch

    fun search(query: String){
        launch {
            _search.postValue(query)
            searchProductUseCase.run(SearchProductUseCase.Params(query))
                .onStart { setLoading(true)  }
                .onEmpty { _failure.postValue(Failure.ServerError(null)) }
                .catch { handleFailure(it) }
                .collect {
                    _searchResponse = it
                    _result.postValue(mapper.mapFromList(it.results))
                }
        }
    }

    fun getSelectedProduct() = _selected
    fun setSelectedProduct(position: Int){
        _selected = _searchResponse.results[position]
    }
}