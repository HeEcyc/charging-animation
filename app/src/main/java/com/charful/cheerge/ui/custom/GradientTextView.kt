package com.charful.cheerge.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {
    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        doOnLayout(changed)
    }

    private fun doOnLayout(changed: Boolean) {
        if (changed) {
            paint.shader = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                arrayOf(
                    Color.parseColor("#141414"),
                    Color.parseColor("#FFFFFF")
                ).toIntArray(),
                null,
                Shader.TileMode.CLAMP
            )
        }
    }
}