package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Installments (
    @SerializedName("quantity")
    val quantity: Long,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("rate")
    val rate: Long,
    @SerializedName("currency_id")
    val currencyID: String
)
