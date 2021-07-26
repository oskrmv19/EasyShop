package com.oskr19.easyshop.domain.dto

data class Variation (
    val id: Long,
    val price: Long,
    val attributeCombinations: List<AttributeCombination>,
    val availableQuantity: Long,
    val soldQuantity: Long,
    val saleTerms: List<Any?>,
    val pictureIDS: List<String>,
    val catalogProductID: Any? = null
)