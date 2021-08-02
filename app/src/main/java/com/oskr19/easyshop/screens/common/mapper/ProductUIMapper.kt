package com.oskr19.easyshop.screens.common.mapper

import android.content.Context
import com.oskr19.easyshop.R
import com.oskr19.easyshop.core.domain.mapper.ListMapper
import com.oskr19.easyshop.core.utils.NumberFormats
import com.oskr19.easyshop.screens.common.dto.Attribute
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI

/**
 * Created by oscar.vergara on 27/07/2021
 */
class ProductUIMapper(private val context: Context) : ListMapper<ProductSearch, ProductUI> {
    override fun mapFrom(type: ProductSearch): ProductUI {
        val productUI = ProductUI()
        productUI.id = type.id
        productUI.title = type.title
        productUI.status = type.status
        productUI.warranty = type.warranty
        productUI.pictures = type.pictures
        productUI.thumbnail = type.thumbnail
        productUI.condition = findAttribute(type.attributes,"ITEM_CONDITION")
        productUI.attributes = type.attributes

        productUI.shipping = if (type.shipping?.freeShipping == true) context.getString(R.string.free_shipping) else ""
        productUI.price = type.price?.let { NumberFormats.toMoney(it) }

        setInfo(productUI, type)

        return productUI
    }

    private fun setInfo(productUI: ProductUI, type: ProductSearch) {
        type.installments?.let {

            productUI.info1 =String.format(
                context.getString(R.string.installment_template),
                it.quantity, NumberFormats.toMoney(it.amount), it.currencyID
            )

            productUI.info2 = String.format(
                context.getString(R.string.address_template),
                type.address?.stateName, type.address?.cityName
            )

        }

        if (type.installments == null) {
            productUI.info1 = String.format(
                context.getString(R.string.kilometer_year_template),
                findAttribute(type.attributes, "VEHICLE_YEAR"),
                findAttribute(type.attributes, "KILOMETERS"),
            )

            productUI.info2 = type.address?.stateName
        }
    }

    private fun findAttribute(list: List<Attribute>?, name: String): String {
        val attr = list?.let { it.find { at -> name == at.id } }
        return attr?.valueName ?: ""
    }

    override fun mapTo(type: ProductUI): ProductSearch {
        return ProductSearch()
    }
}