package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

open class Product {
    @SerializedName("id")
    val id: String = ""
    @SerializedName("site_id")
    var siteID: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("subtitle")
    var subtitle: Any? = null
    @SerializedName("seller_id")
    var sellerID: Long? = null
    @SerializedName("category_id")
    var categoryID: String? = null
    @SerializedName("price")
    var price: Long? = null
    @SerializedName("base_price")
    var basePrice: Long? = null
    @SerializedName("original_price")
    var originalPrice: Long? = null
    @SerializedName("currency_id")
    var currencyID: String? = null
    @SerializedName("initial_quantity")
    var initialQuantity: Long? = null
    @SerializedName("available_quantity")
    var availableQuantity: Long? = null
    @SerializedName("sold_quantity")
    var soldQuantity: Long? = null
    @SerializedName("condition")
    var condition: String? = null
    @SerializedName("permalink")
    var permalink: String? = null
    @SerializedName("thumbnail")
    var thumbnail: String? = null
    @SerializedName("secure_thumbnail")
    var secureThumbnail: String? = null
    @SerializedName("pictures")
    var pictures: List<Picture>? = null
    @SerializedName("accepts_mercadopago")
    var acceptsMercadopago: Boolean? = null
    @SerializedName("seller_contact")
    var sellerContact: Any? = null
    @SerializedName("attributes")
    var attributes: List<Attribute>? = null
    @SerializedName("warnings")
    var warnings: List<Any?>? = null
    @SerializedName("variations")
    var variations: List<Variation>? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("warranty")
    var warranty: String? = null
    @SerializedName("health")
    var health: Double? = null
    @SerializedName("seller_address")
    var sellerAddress: SellerAddress? = null
    var favorite: Boolean = false
}
