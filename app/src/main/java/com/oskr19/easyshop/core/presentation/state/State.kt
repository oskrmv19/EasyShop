package com.oskr19.easyshop.core.presentation.state

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.oskr19.easyshop.core.presentation.UiEvent


/**
 * Created by oscar.vergara on 1/08/2021
 */
open class State: BaseObservable() {

    private var uiEvent: UiEvent? = null

    fun setEvent(event: UiEvent){
        uiEvent = event
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.disconnected)
        notifyPropertyChanged(BR.finished)
        notifyPropertyChanged(BR.error)
    }

    @Bindable
    fun isLoading() = uiEvent is UiEvent.LOADING

    @Bindable
    fun isDisconnected() = uiEvent is UiEvent.DISCONNECTED

    @Bindable
    fun isFinished() = uiEvent is UiEvent.FINISHED

    @Bindable
    fun isError() = uiEvent is UiEvent.ERROR

}