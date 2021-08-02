package com.oskr19.easyshop.screens.detail.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.detail.domain.usecase.GetDescriptionUseCase
import com.oskr19.easyshop.screens.detail.domain.usecase.GetDetailProductUseCase
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by oscar.vergara on 28/07/2021
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val mapper: DetailProductUIMapper,
    private val getDetailProductUseCase: GetDetailProductUseCase,
    private val getDescriptionUseCase: GetDescriptionUseCase
) : BaseViewModel(application) {

    private val _detail = MutableLiveData<ProductUI>()
    val detail: LiveData<ProductUI> get() = _detail

    private lateinit var product: ProductSearch
    private lateinit var detailResponse: DetailResponse

    fun getDetail() {
        launch {
            getDetailProductUseCase.run(GetDetailProductUseCase.Params(product.id))
                .onStart { setEventLoading() }
                .onEmpty { setEventError(Failure.ServerError()) }
                .catch { handleFailure(it as Failure) }
                .collect {
                    detailResponse = it
                    getDescription()
                }
        }
    }

    private fun getDescription() {
        launch {
            getDescriptionUseCase.run(GetDescriptionUseCase.Params(product.id))
                .onStart { setEventLoading() }
                .onEmpty { setEventError(Failure.ServerError()) }
                .catch {
                    handleFailure(it as Failure)
                    _detail.postValue(mapper.mapTo(detailResponse))
                    setEventFinished()
                }
                .collect { resp ->
                    val productUI = mapper.mapTo(detailResponse)
                    productUI.description = resp
                    _detail.postValue(productUI)
                    setEventFinished()
                }
        }
    }

    fun setProduct(productSearch: ProductSearch) {
        product = productSearch
    }
}