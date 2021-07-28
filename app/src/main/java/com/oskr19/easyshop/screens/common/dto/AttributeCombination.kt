package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class AttributeCombination (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value_id")
    val valueID: String,
    @SerializedName("value_name")
    val valueName: String,
    @SerializedName("value_struct")
    val valueStruct: Any? = null,
    @SerializedName("values")
    val values: List<Value>
)