package com.funny.charging_app.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import com.funny.charging_app.BR
import com.funny.charging_app.databinding.*
import com.funny.charging_app.repository.background.display.BatteryLevelReceiver
import com.funny.charging_app.repository.preferences.Preferences

enum class AnimationItem(private val animationBindingClass: Class<out ViewDataBinding>) {

    A1(A1Binding::class.java),
    A2(A2Binding::class.java),
    A3(A3Binding::class.java),
    A4(A4Binding::class.java),
    A5(A5Binding::class.java),
    A6(A6Binding::class.java),
    A7(A7Binding::class.java),
    A8(A8Binding::class.java);

    val isSelected: ObservableBoolean by lazy { ObservableBoolean(Preferences.selectedAnimation == this) }
    val batteryLevelHolder = BatteryLevelHolder()
    fun inflateAnimationView(context: Context): View = animationBindingClass
        .getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        .invoke(null, LayoutInflater.from(context), null, false)
        .let { it as ViewDataBinding }
        .apply { setVariable(BR.blHolder, batteryLevelHolder) }
        .root

    class BatteryLevelHolder {
        val batteryLevel: ObservableField<String> = BatteryLevelReceiver.batteryLevel
    }

    fun setBatteryLevel(text: String) {
        batteryLevelHolder.batteryLevel.set(text)
    }

}
