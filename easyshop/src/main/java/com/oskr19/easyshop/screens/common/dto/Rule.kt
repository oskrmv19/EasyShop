package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Rule (
    @SerializedName("default")
    val default: Boolean,
    @SerializedName("free_mode")
    val freeMode: String,
    @SerializedName("free_shipping_flag")
    val freeShippingFlag: Boolean,
    @SerializedName("value")
    val value: Any? = null
)
