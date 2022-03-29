package com.ioscharginging.iosanimationation.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import com.ioscharginging.iosanimationation.BR
import com.ioscharginging.iosanimationation.R
import com.ioscharginging.iosanimationation.databinding.*
import com.ioscharginging.iosanimationation.repository.background.display.BatteryLevelReceiver
import com.ioscharginging.iosanimationation.repository.preferences.Preferences

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
    A8(R.mipmap.preview_8, A8Binding::class.java);

    val isSelected: ObservableBoolean by lazy { ObservableBoolean(Preferences.selectedAnimation == this) }

    companion object {
        val valuesPopular = values().take(3)
        val valuesOther = values().takeLast(5)
    }

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
