package com.oskr19.easyshop.core.presentation

import com.oskr19.easyshop.core.domain.failure.Failure

/**
 * Created by oscar.vergara on 24/07/2021
 */
sealed class UiEvent {
    object LOADING: UiEvent()
    object FINISHED: UiEvent()
    object DISCONNECTED: UiEvent()

    class ERROR(cause: Failure): UiEvent()
}