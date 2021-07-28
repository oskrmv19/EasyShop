package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Shipping (
    @SerializedName("free_shipping")
    val freeShipping: Boolean,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("logistic_type")
    val logisticType: String,
    @SerializedName("store_pickup")
    val storePickUp: Boolean
)