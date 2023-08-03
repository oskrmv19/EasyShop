package com.oskr19.easyshop.screens.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.common.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.favorite.domain.usecase.GetFavoritesUseCase
import com.oskr19.easyshop.screens.favorite.domain.usecase.RemoveAllFavoritesUseCase
import com.oskr19.easyshop.screens.favorite.presentation.state.NoFavoritesState
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by oscar.vergara on 1/08/2021
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeAllFavoritesUseCase: RemoveAllFavoritesUseCase,
    private val mapper: ProductUIMapper
) : BaseViewModel() {

    private val _search = arrayListOf<ProductSearch>()

    fun getFavorites(): LiveData<List<ProductUI>> {
        val favorites = MutableLiveData<List<ProductUI>>()
       viewModelScope.launch {
            getFavoritesUseCase.run(GetFavoritesUseCase.Params(""))
                .onStart { setEventLoading() }
                .onEmpty {
                    setEventFinished()
                    favorites.postValue(listOf())
                    _status.postValue(NoFavoritesState())
                }
                .catch { handleFailure(it) }
                .collect {
                    _search.clear()
                    _search.addAll(it)
                    favorites.postValue(mapper.mapFromList(it))
                    setEventFinished()
                }
        }
        return favorites
    }

    fun removeAll() {
       viewModelScope.launch {
            removeAllFavoritesUseCase.run(RemoveAllFavoritesUseCase.Params(""))
                .catch { handleFailure(it) }
                .collect {
                    setEventFinished()
                }
        }
    }

    fun getProduct(position: Int) = _search[position]
}
