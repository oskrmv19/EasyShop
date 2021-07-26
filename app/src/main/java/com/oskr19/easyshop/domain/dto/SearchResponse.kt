package com.oskr19.easyshop.domain.dto

data class SearchResponse (
    val siteID: String,
    val countryDefaultTimeZone: String,
    val query: String,
    val paging: Paging,
    val results: List<Any?>
)