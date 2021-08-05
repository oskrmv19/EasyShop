package com.oskr19.easyshop.screens.favorite.domain.request

/**
 * Created by oscar.vergara on 1/08/2021
 */
object FavoriteRequest {
    fun buildFavoriteParams(ids: String?): HashMap<String, Any> {
        val params = HashMap<String, Any>()
        ids?.also {
            params["ids"] = it
        }
        return params
    }
}