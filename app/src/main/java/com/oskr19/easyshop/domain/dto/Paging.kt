package com.oskr19.easyshop.domain.dto

data class Paging (
    val total: Long,
    val primaryResults: Long,
    val offset: Long,
    val limit: Long
)