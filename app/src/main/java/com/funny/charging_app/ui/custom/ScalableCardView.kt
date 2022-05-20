package com.funny.charging_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

class ScalableCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var scaleBy = ScaleType.BY_WIDTH
        set(value) {
            field = value
            requestLayout()
        }
    // percent
    var scalableCornerRadius = 0f
        set(value) {
            field = value
            requestLayout()
        }

    companion object {
        @JvmStatic
        @BindingAdapter("scalableCornerRadius")
        fun setScalableCornerRadius(scv: ScalableCardView, scr: Float) {
            scv.scalableCornerRadius = scr
        }
        @JvmStatic
        @BindingAdapter("scaleType")
        fun setScaleType(scv: ScalableCardView, st: ScaleType) {
            scv.scaleBy = st
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        radius = MeasureSpec.getSize(
            when (scaleBy) {
                ScaleType.BY_WIDTH -> widthMeasureSpec
                ScaleType.BY_HEIGHT -> heightMeasureSpec
            }
        ) * scalableCornerRadius
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    enum class ScaleType { BY_WIDTH, BY_HEIGHT }

}