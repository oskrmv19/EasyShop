package com.oskr19.easyshop.domain.dto

data class Category (
    val id: String,
    val name: String,
    val picture: Any? = null,
    val permalink: Any? = null,
    val totalItemsInThisCategory: Long,
    val pathFromRoot: List<CategoryItem>,
    val childrenCategories: List<ChildrenCategory>
)