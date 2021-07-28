package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Paging (
    @SerializedName("total")
    val total: Long,
    @SerializedName("primary_results")
    val primaryResults: Long,
    @SerializedName("offset")
    val offset: Long,
    @SerializedName("limit")
    val limit: Long
)