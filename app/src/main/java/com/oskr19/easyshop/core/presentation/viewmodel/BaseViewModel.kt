package com.oskr19.easyshop.core.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    protected val _status = MutableLiveData<UiEvent>()
    val status: LiveData<UiEvent?> = _status

    protected val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure?> = _failure

    fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
    }

    fun setLoading(isLoading: Boolean){
        _status.postValue( if (isLoading) UiEvent.LOADING else UiEvent.FINISHED)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}