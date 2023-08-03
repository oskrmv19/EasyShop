package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Picture (
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("secure_url")
    val secureURL: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("max_size")
    val maxSize: String,
    @SerializedName("quality")
    val quality: String
)
