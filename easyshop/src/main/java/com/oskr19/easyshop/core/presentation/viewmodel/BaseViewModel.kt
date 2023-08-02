package com.oskr19.easyshop.core.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.UiEvent
import com.oskr19.easyshop.core.presentation.state.State
import timber.log.Timber

/**
 * Created by oscar.vergara on 24/07/2021
 */
open class BaseViewModel : ViewModel() {

    protected var _state = State()

    protected val _status = MutableLiveData<State>()
    val status: LiveData<State> = _status

    fun handleFailure(failure: Throwable) {
        when (failure) {
            is Failure.NoConnection -> {
                setEventDisconnected()
            }
            is Failure -> {
                Timber.e(failure)
                setEventError(failure)
            }
            else -> {
                Timber.e(failure)
                setEventError(Failure.GenericError())
            }
        }
    }

    fun getState() = _state

    fun setCustomEvent(event: State){
        event.setEvent(UiEvent.FINISHED)
        _state = event
        _status.postValue(event)
    }

    fun setEventLoading(){
        _state.setEvent(UiEvent.LOADING)
        _status.postValue(_state)
    }

    fun setEventFinished(){
        _state.setEvent(UiEvent.FINISHED)
        _status.postValue(_state)
    }

    fun setEventDisconnected(){
        _state.setEvent(UiEvent.DISCONNECTED)
        _status.postValue(_state)
    }

    fun setEventError(failure: Failure) {
        _state.setEvent(UiEvent.ERROR(failure))
        _status.postValue(_state)
    }
}
