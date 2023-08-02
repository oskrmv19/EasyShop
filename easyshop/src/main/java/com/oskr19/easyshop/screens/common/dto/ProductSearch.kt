package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by oscar.vergara on 23/07/2021
 */
open class ProductSearch: Product() {
    @SerializedName("sale_price")
    val salePrice: Double? = null

    @SerializedName("installments")
    val installments: Installments? = null

    @SerializedName("address")
    val address: Address? = null

    @SerializedName("shipping")
    val shipping: Shipping? = null
}
