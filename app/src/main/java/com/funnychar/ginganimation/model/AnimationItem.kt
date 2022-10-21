package com.funnychar.ginganimation.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import com.funnychar.ginganimation.BR
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.databinding.*
import com.funnychar.ginganimation.repository.background.display.BatteryLevelReceiver

enum class AnimationItem(
    val previewPicRes: Int,
    private val animationBindingClass: Class<out ViewDataBinding>
) {

    A1(R.mipmap.preview_1, A1Binding::class.java),
    A2(R.mipmap.preview_2, A2Binding::class.java),
    A3(R.mipmap.preview_3, A3Binding::class.java),
    A4(R.mipmap.preview_4, A4Binding::class.java),
    A5(R.mipmap.preview_5, A5Binding::class.java),
    A6(R.mipmap.preview_6, A6Binding::class.java),
    A7(R.mipmap.preview_7, A7Binding::class.java),
    A8(R.mipmap.preview_8, A8Binding::class.java),
    A9(R.mipmap.preview_9, A9Binding::class.java);

    fun inflateAnimationView(context: Context): View = animationBindingClass
        .getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        .invoke(null, LayoutInflater.from(context), null, false)
        .let { it as ViewDataBinding }
        .apply {
            setVariable(BR.blHolder, BatteryLevelHolder())
        }
        .root

    class BatteryLevelHolder {
        val batteryLevel: ObservableField<String> = BatteryLevelReceiver.batteryLevel
    }

}
