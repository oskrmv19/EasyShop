package com.oskr19.easyshop.data.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by oscar.vergara on 25/07/2021
 */
@Entity
class ProductEntity(@PrimaryKey val id: Long, val title: String)