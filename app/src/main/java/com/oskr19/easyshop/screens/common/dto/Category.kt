package com.oskr19.easyshop.screens.common.dto

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String? = null,
    @SerializedName("permalink")
    val permalink: String? = null,
    @SerializedName("total_items_in_this_category")
    val totalItemsInThisCategory: Long,
    @SerializedName("path_from_root")
    val pathFromRoot: List<PathItem>?,
    @SerializedName("children_categories")
    val childrenCategories: List<Category>
)