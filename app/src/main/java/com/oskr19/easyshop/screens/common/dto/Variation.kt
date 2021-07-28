package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Variation (
    @SerializedName("id")
    val id: Long,
    @SerializedName("price")
    val price: Long,
    @SerializedName("attribute_combinations")
    val attributeCombinations: List<AttributeCombination>,
    @SerializedName("available_quantity")
    val availableQuantity: Long,
    @SerializedName("sold_quantity")
    val soldQuantity: Long,
    @SerializedName("sale_terms")
    val saleTerms: List<Any?>,
    @SerializedName("picture_ids")
    val pictureIDS: List<String>,
    @SerializedName("catalog_product_id")
    val catalogProductID: Long? = null
)