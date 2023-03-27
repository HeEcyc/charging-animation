package com.juno.ca.model.animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.juno.ca.BR
import com.juno.ca.R
import com.juno.ca.databinding.*
import com.juno.ca.repository.background.display.BatteryLevelReceiver

enum class PresetAnimation(
    val previewPicRes: Int,
    private val animationBindingClass: Class<out ViewDataBinding>
) : Animation {

    A01(R.mipmap.preview_01, A01Binding::class.java),
    A02(R.mipmap.preview_02, A02Binding::class.java),
    A03(R.mipmap.preview_03, A03Binding::class.java),
    A04(R.mipmap.preview_04, A04Binding::class.java),
    A05(R.mipmap.preview_05, A05Binding::class.java),
    A06(R.mipmap.preview_06, A06Binding::class.java),
    A07(R.mipmap.preview_07, A07Binding::class.java),
    A08(R.mipmap.preview_08, A08Binding::class.java);

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
