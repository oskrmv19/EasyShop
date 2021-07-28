package com.oskr19.easyshop.screens.search.presentation

/**
 * Created by oscar.vergara on 27/07/2021
 */
interface ProductItemListener {
    fun onItemClick(position: Int)
    fun setFavorite(position: Int, isFavorite: Boolean)
}