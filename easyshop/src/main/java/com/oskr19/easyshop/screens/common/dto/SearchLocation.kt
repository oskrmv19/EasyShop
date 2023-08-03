package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class SearchLocation (
    @SerializedName("neighborhood")
    val neighborhood: City,
    @SerializedName("city")
    val city: City,
    @SerializedName("state")
    val state: City
)
