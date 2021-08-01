package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class SellerAddress (
    @SerializedName("city")
    val city: City,
    @SerializedName("state")
    val state: City,
    @SerializedName("country")
    val country: City,
    @SerializedName("search_location")
    val searchLocation: SearchLocation,
    @SerializedName("id")
    val id: Long
)