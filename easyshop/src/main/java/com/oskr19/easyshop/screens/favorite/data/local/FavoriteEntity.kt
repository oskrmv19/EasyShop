package com.oskr19.easyshop.screens.favorite.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by oscar.vergara on 1/08/2021
 */
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    var productId: String
)
