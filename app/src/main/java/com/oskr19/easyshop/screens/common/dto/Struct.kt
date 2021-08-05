package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Struct (
    @SerializedName("number")
    val number: Double,
    @SerializedName("unit")
    val unit: String
)