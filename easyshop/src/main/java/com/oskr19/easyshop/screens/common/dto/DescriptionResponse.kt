package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class DescriptionResponse (
    @SerializedName("plain_text")
    val plainText: String
)
