package com.funnychar.ginganimation.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

class FScalableFCardFViewF @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var scaleBy = ScaleType.BY_WIDTH
        set(value) {
            System.currentTimeMillis()
            field = value
            System.currentTimeMillis()
            requestLayout()
            System.currentTimeMillis()
        }
    // percent
    var scalableCornerRadius = 0f
        set(value) {
            System.currentTimeMillis()
            field = value
            System.currentTimeMillis()
            requestLayout()
            System.currentTimeMillis()
        }

    companion object {
        @JvmStatic
        @BindingAdapter("scalableCornerRadius")
        fun setScalableCornerRadius(scv: FScalableFCardFViewF, scr: Float) {
            System.currentTimeMillis()
            scv.scalableCornerRadius = scr
            System.currentTimeMillis()
        }
        @JvmStatic
        @BindingAdapter("scaleType")
        fun setScaleType(scv: FScalableFCardFViewF, st: ScaleType) {
            System.currentTimeMillis()
            scv.scaleBy = st
            System.currentTimeMillis()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        System.currentTimeMillis()
        radius = MeasureSpec.getSize(
            when (scaleBy) {
                ScaleType.BY_WIDTH -> widthMeasureSpec
                ScaleType.BY_HEIGHT -> heightMeasureSpec
            }
        ) * scalableCornerRadius
        System.currentTimeMillis()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        System.currentTimeMillis()
    }

    enum class ScaleType { BY_WIDTH, BY_HEIGHT }

}