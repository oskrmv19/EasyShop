package com.oskr19.easyshop.core.presentation.dialog

/**
 * Created by oscar.vergara on 30/06/2021
 */
interface DialogAction {
    fun onPositive()
}

interface NegativeAction : DialogAction {
    fun onNegative()
}

interface FullActions : NegativeAction
