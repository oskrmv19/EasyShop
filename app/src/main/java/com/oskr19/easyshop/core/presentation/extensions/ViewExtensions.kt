package com.oskr19.easyshop.core.presentation.extensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.oskr19.easyshop.R

/**
 * Created by oscar.vergara on 24/07/2021
 */
@BindingAdapter("android:showHide")
fun showHideView(view: View, show: Boolean) {
    view.showHide(show)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    Glide.with(context)
        .load(uri)
        .transform(GranularRoundedCorners(15f, 15f, 15f, 15f))
        .placeholder(progressDrawable)
        .error(R.drawable.no_image)
        .into(this)
}

fun View.showHide(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.setHeightPercent(percent: Double) {
    val params = this.layoutParams as ConstraintLayout.LayoutParams
    params.height = (Resources.getSystem().displayMetrics.heightPixels * percent).toInt()
    this.layoutParams = params
    this.requestLayout()
}