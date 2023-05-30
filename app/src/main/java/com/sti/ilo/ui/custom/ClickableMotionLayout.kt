package com.sti.ilo.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class ClickableMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MotionLayout(context, attrs, defStyle), CoroutineScope by MainScope() {

    val listeners = mutableMapOf<View, () -> Unit>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && event.eventTime - event.downTime < ViewConfiguration.getTapTimeout()) {
            val target = children.toList().reversed().firstOrNull {
                Rect(
                    it.x.toInt(),
                    it.y.toInt(),
                    it.x.toInt() + it.width,
                    it.y.toInt() + it.height
                ).contains(event.x.toInt(), event.y.toInt())
            }
            listeners.getOrDefault(target) {}.invoke()
        }
        return super.onTouchEvent(event)
    }

}
