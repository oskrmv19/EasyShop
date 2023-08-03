package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class FreeMethod (
    @SerializedName("id")
    val id: Long,
    @SerializedName("rule")
    val rule: Rule
)
