package com.smooth.battery.model.animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.smooth.battery.BR
import com.smooth.battery.R
import com.smooth.battery.databinding.*
import com.smooth.battery.repository.background.display.BatteryLevelReceiver

enum class PresetAnimation(
    val previewPicRes: Int,
    private val animationBindingClass: Class<out ViewDataBinding>
) : Animation {

    A1(R.mipmap.preview_1, A1Binding::class.java),
    A2(R.mipmap.preview_2, A2Binding::class.java),
    A3(R.mipmap.preview_3, A3Binding::class.java),
    A4(R.mipmap.preview_4, A4Binding::class.java),
    A5(R.mipmap.preview_5, A5Binding::class.java),
    A6(R.mipmap.preview_6, A6Binding::class.java),
    A7(R.mipmap.preview_7, A7Binding::class.java),
    A8(R.mipmap.preview_8, A8Binding::class.java);

    override fun inflateAnimationView(context: Context): View = animationBindingClass
        .getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        .invoke(null, LayoutInflater.from(context), null, false)
        .let { it as ViewDataBinding }
        .apply {
            setVariable(BR.blHolder, BatteryLevelReceiver.BatteryLevelHolder())
        }
        .root

    override fun getPrefString(): String = name

}
