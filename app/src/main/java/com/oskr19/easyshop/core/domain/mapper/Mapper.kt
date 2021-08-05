package com.oskr19.easyshop.core.domain.mapper

/**
 * Created by oscar.vergara on 24/07/2021
 */
interface Mapper<I, O> {
    fun mapFrom(type: I): O
    fun mapTo(type: O): I
}