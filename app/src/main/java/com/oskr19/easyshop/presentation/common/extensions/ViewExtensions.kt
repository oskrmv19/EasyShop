package com.oskr19.easyshop.presentation.common.extensions

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by oscar.vergara on 24/07/2021
 */
@BindingAdapter("android:showHide")
fun showHideView(view: View, show: Boolean){
    view.showHide(show)
}

fun View.showHide(boolean: Boolean){
    this.visibility = if(boolean) View.VISIBLE else View.GONE
}