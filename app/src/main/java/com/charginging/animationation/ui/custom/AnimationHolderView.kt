package com.charginging.animationation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import com.airbnb.lottie.LottieAnimationView
import com.charginging.animationation.model.AnimationItem
import com.charginging.animationation.model.ClosableWindow
import com.charginging.animationation.model.ClosableWindows

class AnimationHolderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ClosableWindow {
    var isPreview = false

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setOnClickListener { if (!isPreview) ClosableWindows.close(this) }
    }

    fun showView(animationItem: AnimationItem, isStopOnStart: Boolean = false) {
        addView(animationItem.inflateAnimationView(context), layoutParams)
        if (isStopOnStart) pauseAnimation()
    }

    override fun close() = context.getSystemService(WindowManager::class.java).removeView(this)

    fun playAnimation() {
        playAnimation(this)
    }

    fun pauseAnimation() {
        pauseAnimation(this)
    }

    private fun playAnimation(viewGroup: ViewGroup) {
        viewGroup.forEach {
            if (it is LottieAnimationView) {
                if (!it.isAnimating) it.playAnimation()
            } else if (it is ViewGroup) playAnimation(it)
        }
    }

    private fun pauseAnimation(viewGroup: ViewGroup) {
        viewGroup.forEach {
            if (it is LottieAnimationView) it.pauseAnimation()
            else if (it is ViewGroup) pauseAnimation(it)
        }
    }
}