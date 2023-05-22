package com.kapi.ca.ui.custom

import android.content.Context
import android.graphics.BlendMode
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.kapi.ca.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class CircleProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var progress: Float
        get() = impl.progress
        set(value) { impl.progress = value }

    private val impl = ImplCircleProgressView(context)

    companion object {
        @JvmStatic
        @BindingAdapter("progress")
        fun setProgress(cpv: CircleProgressView, p: Float) {
            cpv.progress = p
        }
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ScalableCardView,
            0, 0
        ).apply {
            try {
                progress = getFloat(R.styleable.CircleProgressView_progress, 0f)
            } finally {
                recycle()
            }
        }
        addView(
            impl,
            LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.MATCH_CONSTRAINT).apply {
                topToTop = LayoutParams.PARENT_ID
                bottomToBottom = LayoutParams.PARENT_ID
                startToStart = LayoutParams.PARENT_ID
                endToEnd = LayoutParams.PARENT_ID
                dimensionRatio = "1:1"
            }
        )
    }

    private class ImplCircleProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : ConstraintLayout(context, attrs, defStyleAttr) {

        var progress: Float = 0f
            set(value) {
                field = value
                invalidate()
            }

        private val ringPaint = Paint().apply {
            color = Color.parseColor("#8000DDB3")
            style = Paint.Style.STROKE
        }

        private val circlePaint = Paint().apply {
            color = Color.parseColor("#8000DDB3")
            blendMode = BlendMode.SRC
            setAlpha(0.5f)
        }

        init {
            setBackgroundColor(Color.TRANSPARENT)
        }

        private val ringWidth get() = width * 0.07f

        private val circleRadius get() = ringWidth / 2f

        private val ringRadius get() = width / 2f - circleRadius

        private val center get() = width / 2f

        override fun onDraw(canvas: Canvas) {
            ringPaint.strokeWidth = ringWidth
            canvas.drawArc(
                circleRadius,
                circleRadius,
                width.toFloat() - circleRadius,
                height.toFloat() - circleRadius,
                90f,
                360 * progress,
                false,
                ringPaint
            )
            val angleFirst = 0.5 * PI
            val xFirst = ringRadius * cos(angleFirst) + center
            val yFirst = ringRadius * sin(angleFirst) + center
            canvas.drawCircle(xFirst.toFloat(), yFirst.toFloat(), circleRadius, circlePaint)
            val angleSecond = angleFirst + 2 * PI * progress
            val xSecond = ringRadius * cos(angleSecond) + center
            val ySecond = ringRadius * sin(angleSecond) + center
            canvas.drawCircle(xSecond.toFloat(), ySecond.toFloat(), circleRadius, circlePaint)
        }

    }

}