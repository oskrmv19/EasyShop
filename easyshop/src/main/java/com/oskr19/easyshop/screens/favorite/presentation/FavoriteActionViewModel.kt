package com.oskr19.easyshop.screens.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.favorite.domain.usecase.SetFavoriteUseCase
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
class FavoriteActionViewModel @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : BaseViewModel() {

    fun setFavorite(productId: String, isFavorite: Boolean): LiveData<String> {
        val liveData = MutableLiveData<String>()
       viewModelScope.launch {
            setFavoriteUseCase.run(SetFavoriteUseCase.Params(productId, isFavorite))
                .onStart { setEventLoading() }
                .onEmpty { setEventError(Failure.ServerError()) }
                .catch { handleFailure(it) }
                .collect {
                    setEventFinished()
                    liveData.postValue(productId)
                }
        }

        return liveData
    }
}
