package com.oskr19.easyshop.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.roundToLong

object NumberFormats {
    fun toMoney(value: Long): String {
        val formatSymbols = DecimalFormatSymbols()
        formatSymbols.decimalSeparator = ','
        formatSymbols.groupingSeparator = '.'
        formatSymbols.perMill = '.'
        val format = DecimalFormat("'$ '#,###", formatSymbols)
        return format.format(value)
    }

    fun toMoney(value: Double): String {
        return toMoney(value.roundToLong())
    }
}