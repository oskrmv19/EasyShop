package com.oskr19.easyshop.core.utils

/**
 * Created by oscar.vergara on 30/07/2021
 */
sealed class TableLayoutThemes {

    fun isOrange() = this is Orange
    fun isGray() = this is Gray

    object Orange: TableLayoutThemes()
    object Gray: TableLayoutThemes()
}