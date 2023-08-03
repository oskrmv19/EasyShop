package com.oskr19.easyshop.screens.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import com.oskr19.easyshop.screens.detail.domain.usecase.GetDescriptionUseCase
import com.oskr19.easyshop.screens.detail.domain.usecase.GetDetailProductUseCase
import com.oskr19.easyshop.screens.lately_seen.domain.usecase.SaveLatelySeenUseCase
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by oscar.vergara on 28/07/2021
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mapper: DetailProductUIMapper,
    private val getDetailProductUseCase: GetDetailProductUseCase,
    private val getDescriptionUseCase: GetDescriptionUseCase,
    private val saveLatelySeenUseCase: SaveLatelySeenUseCase
) : BaseViewModel() {

    private val _detail = MutableLiveData<ProductUI>()
    val detail: LiveData<ProductUI> get() = _detail

    private lateinit var detailResponse: DetailResponse

    fun getDetail(productId: String) {
       viewModelScope.launch {
            getDetailProductUseCase.run(GetDetailProductUseCase.Params(productId))
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
       viewModelScope.launch {
            getDescriptionUseCase.run(GetDescriptionUseCase.Params(detailResponse.id))
                .onStart { setEventLoading() }
                .onEmpty { saveLatelySeen(detailResponse.id) }
                .catch {
                    _detail.postValue(mapper.mapTo(detailResponse))
                    saveLatelySeen(detailResponse.id)
                }
                .collect { resp ->
                    val productUI = mapper.mapTo(detailResponse)
                    productUI.description = resp
                    _detail.postValue(productUI)
                    saveLatelySeen(detailResponse.id)
                }
        }
    }

    private suspend fun saveLatelySeen(productId: String) {
        saveLatelySeenUseCase.run(SaveLatelySeenUseCase.Params(productId))
            .onStart { setEventLoading() }
            .catch { setEventFinished() }
            .collect {
                setEventFinished()
            }
    }
}
