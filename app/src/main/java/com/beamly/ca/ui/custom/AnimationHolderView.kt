package com.beamly.ca.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.beamly.ca.model.ClosableWindow
import com.beamly.ca.model.ClosableWindows
import com.beamly.ca.repository.preferences.Preferences

class AnimationHolderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ClosableWindow {

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setOnClickListener { ClosableWindows.close(this) }
        addView(
            Preferences.selectedAnimation.inflateAnimationView(context),
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
    }

    override fun close() = context.getSystemService(WindowManager::class.java).removeView(this)

}