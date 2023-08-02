package com.oskr19.easyshop.screens.lately_seen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.common.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.favorite.presentation.state.NoLatelySeenState
import com.oskr19.easyshop.screens.lately_seen.domain.usecase.GetLatelySeenUseCase
import com.oskr19.easyshop.screens.lately_seen.domain.usecase.SaveLatelySeenUseCase
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by oscar.vergara on 1/08/2021
 */
@HiltViewModel
class LatelySeenViewModel @Inject constructor(
    private val getLatelySeenUseCase: GetLatelySeenUseCase,
    private val saveLatelySeenUseCase: SaveLatelySeenUseCase,
    private val mapper: ProductUIMapper
) : BaseViewModel() {

    private val _search = arrayListOf<ProductSearch>()

    fun fetchAll(): LiveData<List<ProductUI>> {
        val liveData = MutableLiveData<List<ProductUI>>()
       viewModelScope.launch {
            getLatelySeenUseCase.run(GetLatelySeenUseCase.Params(""))
                .onStart {
                    setEventLoading()
                }
                .onEmpty {
                    setEventFinished()
                    _status.postValue(NoLatelySeenState())
                }
                .onCompletion { setEventFinished() }
                .catch { handleFailure(it) }
                .collect {
                    _search.clear()
                    _search.addAll(it)
                    liveData.postValue(mapper.mapFromList(it))
                }
        }
        return liveData
    }

    fun getProduct(position: Int) = _search[position]
}
