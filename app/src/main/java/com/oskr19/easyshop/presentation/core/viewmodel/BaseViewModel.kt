package com.oskr19.easyshop.presentation.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.domain.core.failure.Failure
import com.oskr19.easyshop.presentation.core.UiEvent

/**
 * Created by oscar.vergara on 24/07/2021
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val _status = MutableLiveData<UiEvent>()
    val status: LiveData<UiEvent?> = _status

    protected val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure?> = _failure

    fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
    }
}