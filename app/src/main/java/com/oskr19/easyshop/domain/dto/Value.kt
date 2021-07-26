package com.oskr19.easyshop.domain.dto

data class Value (
    val id: String? = null,
    val name: String,
    val source: Long? = null,
    val struct: Struct? = null
)