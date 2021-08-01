package com.oskr19.easyshop.screens.search.presentation.model

import com.oskr19.easyshop.screens.common.dto.Attribute
import com.oskr19.easyshop.screens.common.dto.Picture

/**
 * Created by oscar.vergara on 27/07/2021
 */
class ProductUI {
    var id: String? = ""
    var title: String? = ""
    var description: String? = null
    var price: String? = ""
    var condition: String? = ""
    var thumbnail: String? = ""
    var pictures: List<Picture>? = listOf()
    var attributes: List<Attribute>? = listOf()
    var status: String? = ""
    var warranty: String? = ""
    var info1: String? = ""
    var info2: String? = ""
    var shipping: String? = ""
}