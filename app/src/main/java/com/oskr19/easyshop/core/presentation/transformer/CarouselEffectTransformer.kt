package com.oskr19.easyshop.core.presentation.transformer

import android.content.Context
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by oscar.vergara on 29/07/2021
 */
class CarouselEffectTransformer(context: Context, private val viewPager: ViewPager2) : ViewPager2.PageTransformer {
    private val maxTranslateOffsetX: Int = dp2px(context, DIP_VALUE.toFloat())

    override fun transformPage(view: View, position: Float) {

        val leftInScreen = view.left - viewPager.scrollX
        val centerXInViewPager = leftInScreen + view.measuredWidth / INT_2
        val offsetX = centerXInViewPager - viewPager.measuredWidth / INT_2
        val offsetRate = offsetX.toDouble() * FLOAT / viewPager.measuredWidth
        val scaleFactor = 1 - Math.abs(offsetRate)
        if (scaleFactor > 0) {
            view.scaleX = scaleFactor.toFloat()
            view.scaleY = scaleFactor.toFloat()
            view.translationX = (-maxTranslateOffsetX * offsetRate).toFloat()
        }
        ViewCompat.setElevation(view, scaleFactor.toFloat())
    }

    companion object {
        private const val DIP_VALUE = 180
        private const val INT_2 = 2
        private const val FLOAT = 0.38f
        private const val FLOAT1 = 0.5f

        /**
         * Dp to pixel conversion
         */
        private fun dp2px(context: Context, dipValue: Float): Int {
            val m = context.resources.displayMetrics.density
            return (dipValue.toDouble() * m + FLOAT1).toInt()
        }
    }

}