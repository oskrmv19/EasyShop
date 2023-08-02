package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("site_id")
    val siteID: String,
    @SerializedName("country_default_time_zone")
    val countryDefaultTimeZone: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("paging")
    val paging: Paging,
    @SerializedName("results")
    val results: List<ProductSearch>
)
