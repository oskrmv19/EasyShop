package com.oskr19.easyshop.domain.dto

data class AttributeCombination (
    val id: String,
    val name: String,
    val valueID: String,
    val valueName: String,
    val valueStruct: Any? = null,
    val values: List<Value>
)