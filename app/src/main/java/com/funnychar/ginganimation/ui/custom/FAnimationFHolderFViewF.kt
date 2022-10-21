package com.funnychar.ginganimation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.funnychar.ginganimation.model.FClosableFWindowF
import com.funnychar.ginganimation.model.FClosableFWindowsF
import com.funnychar.ginganimation.repository.preferences.FPreferencesF

class FAnimationFHolderFViewF @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), FClosableFWindowF {

    init {
        System.currentTimeMillis()
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        System.currentTimeMillis()
        setOnClickListener { FClosableFWindowsF.close(this) }
        System.currentTimeMillis()
        addView(
            FPreferencesF.selectedAnimation.inflateAnimationView(context),
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
        System.currentTimeMillis()
    }

    override fun close() = context.getSystemService(WindowManager::class.java).removeView(this)

}