package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

open class DetailResponse: ProductSearch() {
    @SerializedName("official_store_id")
    var officialStoreID: Any? = null
    @SerializedName("sale_terms")
    var saleTerms: List<Attribute>? = null
    @SerializedName("buying_mode")
    var buyingMode: String? = null
    @SerializedName("listing_type_id")
    var listingTypeID: String? = null
    @SerializedName("start_time")
    var startTime: String? = null
    @SerializedName("stop_time")
    var stopTime: String? = null
    @SerializedName("video_id")
    var videoID: Any? = null
    @SerializedName("descriptions")
    var descriptions: List<Description>? = null
    @SerializedName("non_mercado_pago_payment_methods")
    var nonMercadoPagoPaymentMethods: List<Any?>? = null
    @SerializedName("international_delivery_mode")
    var internationalDeliveryMode: String? = null
    @SerializedName("coverage_areas")
    var coverageAreas: List<Any?>? = null
    @SerializedName("listing_source")
    var listingSource: String? = null
    @SerializedName("sub_status")
    var subStatus: List<Any?>? = null
    @SerializedName("tags")
    var tags: List<String>? = null
    @SerializedName("domain_id")
    var domainID: String? = null
    @SerializedName("parent_item_id")
    var parentItemID: Any? = null
    @SerializedName("differential_pricing")
    var differentialPricing: Any? = null
    @SerializedName("deal_ids")
    var dealIDS: List<Any?>? = null
    @SerializedName("automatic_relist")
    var automaticRelist: Boolean? = null
    @SerializedName("catalog_listing")
    var catalogListing: Boolean? = null
    @SerializedName("channels")
    var channels: List<String>? = null
}


