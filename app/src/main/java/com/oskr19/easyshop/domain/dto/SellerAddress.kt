package com.oskr19.easyshop.domain.dto

data class SellerAddress (
    val city: City,
    val state: City,
    val country: City,
    val searchLocation: SearchLocation,
    val id: Long
)