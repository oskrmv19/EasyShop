package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class MultiSearchResponse (
    @SerializedName("code")
    val code: Int,
    @SerializedName("body")
    val body: ProductSearch,
)

