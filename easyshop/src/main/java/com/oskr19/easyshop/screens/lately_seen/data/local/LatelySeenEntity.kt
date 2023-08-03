package com.oskr19.easyshop.screens.lately_seen.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by oscar.vergara on 1/08/2021
 */
@Entity(tableName = "lately_seen")
data class LatelySeenEntity(
    @PrimaryKey
    var productId: String
)
