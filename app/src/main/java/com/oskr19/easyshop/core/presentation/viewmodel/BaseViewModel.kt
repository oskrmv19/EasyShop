package com.oskr19.easyshop.core.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.UiEvent
import com.oskr19.easyshop.core.presentation.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Created by oscar.vergara on 24/07/2021
 */
open class BaseViewModel(
    application: Application,
) : AndroidViewModel(application), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected val _state = State()

    private val _status = MutableLiveData<State>()
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

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}