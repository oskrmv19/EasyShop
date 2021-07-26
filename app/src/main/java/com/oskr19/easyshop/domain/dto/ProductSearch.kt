package com.oskr19.easyshop.domain.dto

/**
 * Created by oscar.vergara on 23/07/2021
 */
data class ProductSearch (
    val salePrice: Any? = null,
    val permalink: String,
    val thumbnail: String,
    val installments: Installments,
    val address: Address,
    val shipping: Shipping
)