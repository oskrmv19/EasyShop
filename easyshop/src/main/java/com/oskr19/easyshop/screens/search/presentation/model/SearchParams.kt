package com.oskr19.easyshop.screens.search.presentation.model

/**
 * Created by oscar.vergara on 25/07/2021
 */
class SearchParams {
    var offset: Int = 0
    var query: String? = null
    var category: String? = null
    var sellerId: String? = null
    var lastPage: Boolean = false

    fun removeSeller(): SearchParams {
        sellerId = null
        return this
    }

    fun removeCategory(): SearchParams {
        category = null
        return this
    }

    fun resetOffset(): SearchParams {
        offset = 0
        return this
    }

    fun setLastPage(lastPage: Boolean): SearchParams {
        this.lastPage = lastPage
        return this
    }

    fun setQuery(query: String?): SearchParams {
        this.query = query
        return this
    }

    fun setOffset(offset: Int): SearchParams {
        this.offset = offset
        return this
    }

    fun setCategory(category: String?): SearchParams {
        this.category = category
        return this
    }

    fun setSellerId(seller: String?): SearchParams {
        this.sellerId = seller
        return this
    }
}
