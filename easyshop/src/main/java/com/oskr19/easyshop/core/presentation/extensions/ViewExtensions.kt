package com.oskr19.easyshop.core.presentation.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
    view.background = null
    view.loadImage(url, getProgressDrawable(view.context))
}

@BindingAdapter("android:favorite")
fun loadFavorite(view: TextView, favorite: Boolean) {
    view.loadFavoriteDrawable(favorite)
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        setColorSchemeColors(Color.parseColor("#FBB045"))
        start()
    }
}

fun TextView.loadFavoriteDrawable(favorite: Boolean) {
    this.background =
        ResourcesCompat.getDrawable(
            this.context.resources,
            if (favorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite,
            null
        )
    val colorStateList = ContextCompat.getColorStateList(this.context, R.color.orange)
    this.backgroundTintList = colorStateList
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

fun GridView.expand(){
    val listAdapter: ListAdapter = this.adapter ?: return

    var totalHeight: Int
    val items: Int = listAdapter.count
    val rows: Int

    if(items>0) {
        val listItem: View = listAdapter.getView(0, null, this)
        listItem.measure(0, 0)
        totalHeight = listItem.measuredHeight

        val x: Float
        if (items > numColumns) {
            x = (items / numColumns).toFloat()
            rows = (x + 1).toInt()
            totalHeight *= rows
        }

        val params: ViewGroup.LayoutParams = this.layoutParams
        params.height = totalHeight
        this.layoutParams = params
    }
}
