package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Address (
    @SerializedName("state_id")
    val stateID: String,

    @SerializedName("state_name")
    val stateName: String,

    @SerializedName("city_id")
    val cityID: String,

    @SerializedName("city_name")
    val cityName: String
)
