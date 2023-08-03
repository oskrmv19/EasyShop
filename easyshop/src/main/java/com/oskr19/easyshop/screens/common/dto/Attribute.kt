package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("source")
    val source: Long? = null,
    @SerializedName("value_id")
    val valueID: String? = null,
    @SerializedName("value_name")
    val valueName: String,
    @SerializedName("value_struct")
    val valueStruct: Struct? = null,
    @SerializedName("values")
    val values: List<Value>,
    @SerializedName("attribute_group_id")
    val groupId: String? = null,
    @SerializedName("attribute_group_name")
    val groupName: String? = null
)
