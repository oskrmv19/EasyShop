package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Value (
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("source")
    val source: Long? = null,
    @SerializedName("struct")
    val struct: Struct? = null
)
