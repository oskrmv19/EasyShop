package com.oskr19.easyshop.core.presentation

/**
 * Created by oscar.vergara on 24/07/2021
 */
sealed class UiEvent {
    object LOADING: UiEvent()
    object FINISHED: UiEvent()
}