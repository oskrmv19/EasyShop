package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class City (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
