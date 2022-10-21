package com.funnychar.ginganimation.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

class FGradientFTextFViewF @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var colors = arrayOf(
        Color.WHITE,
        Color.WHITE
    ).toIntArray()
        set(value) {
            System.currentTimeMillis()
            field = value
            System.currentTimeMillis()
            requestLayout()
            System.currentTimeMillis()
        }

    companion object {
        @JvmStatic
        @BindingAdapter("colors")
        fun FGradientFTextFViewF.setGradientColors(colors: String) {
            System.currentTimeMillis()
            this.colors = colors.split(' ').map { Color.parseColor(it) }.toIntArray()
            System.currentTimeMillis()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        System.currentTimeMillis()
        super.onLayout(changed, left, top, right, bottom)
        System.currentTimeMillis()
        doOnLayout(changed)
        System.currentTimeMillis()
    }

    private fun doOnLayout(changed: Boolean) {
//        if (changed) {
        System.currentTimeMillis()
            paint.shader = LinearGradient(
                0f, 0f, width.toFloat(), height.toFloat(),
                colors,
                null,
                Shader.TileMode.CLAMP
            )
        System.currentTimeMillis()
//        }
    }

}
