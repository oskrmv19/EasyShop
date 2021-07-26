package com.oskr19.easyshop.domain.dto

data class Installments (
    val quantity: Long,
    val amount: Double,
    val rate: Long,
    val currencyID: String
)