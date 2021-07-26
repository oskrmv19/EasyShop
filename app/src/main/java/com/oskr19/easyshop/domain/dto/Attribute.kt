package com.oskr19.easyshop.domain.dto

data class Attribute (
    val id: String,
    val name: String,
    val source: Long? = null,
    val valueID: String? = null,
    val valueName: String,
    val valueStruct: Struct? = null,
    val values: List<Value>
)