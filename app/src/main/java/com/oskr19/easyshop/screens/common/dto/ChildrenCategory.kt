package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class ChildrenCategory (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("total_items_in_this_category")
    val totalItemsInThisCategory: Long
)