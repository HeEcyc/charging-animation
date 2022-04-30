package com.smooth.battery.repository.preferences

import android.content.Context
import com.smooth.battery.App
import com.smooth.battery.model.AnimationItem

object Preferences {

    private val prefs = App.instance.getSharedPreferences("charging_animation", Context.MODE_PRIVATE)

    private const val KEY_SELECTED_ANIMATION = "ca_selected_animation"
    private const val KEY_SHOW_WHEN_UNLOCKED = "ca_show_when_unlocked"
    private const val KEY_NOTIFICATION_ARE_ON = "ca_notifications_are_on"
    private const val KEY_FLASH_IS_ON = "ca_flash_is_on"
    private const val KEY_VIBRATION_IS_ON = "ca_vibration_is_on"
    private const val KEY_SOUND_IS_ON = "ca_sound_is_on"
    private const val KEY_WAS_LAUNCHED_BEFORE = "ca_was_launched_before"

    var selectedAnimation: AnimationItem
        get() = AnimationItem.valueOf(prefs.getString(KEY_SELECTED_ANIMATION, null) ?: AnimationItem.values().first().name)
        set(value) {
            AnimationItem.values().forEach { it.isSelected.set(it == value) }
            prefs.edit().putString(KEY_SELECTED_ANIMATION, value.name).apply()
        }

    var showWhenUnlocked: Boolean
        get() = prefs.getBoolean(KEY_SHOW_WHEN_UNLOCKED, true)
        set(value) = prefs.edit().putBoolean(KEY_SHOW_WHEN_UNLOCKED, value).apply()

    var areNotificationsOn: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_ARE_ON, true)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATION_ARE_ON, value).apply()

    var isFlashOn: Boolean
        get() = prefs.getBoolean(KEY_FLASH_IS_ON, true)
        set(value) = prefs.edit().putBoolean(KEY_FLASH_IS_ON, value).apply()

    var isVibrationOn: Boolean
        get() = prefs.getBoolean(KEY_VIBRATION_IS_ON, true)
        set(value) = prefs.edit().putBoolean(KEY_VIBRATION_IS_ON, value).apply()

    var isSoundOn: Boolean
        get() = prefs.getBoolean(KEY_SOUND_IS_ON, true)
        set(value) = prefs.edit().putBoolean(KEY_SOUND_IS_ON, value).apply()

    var wasLaunchedBefore: Boolean
        get() = prefs.getBoolean(KEY_WAS_LAUNCHED_BEFORE, false)
        set(value) = prefs.edit().putBoolean(KEY_WAS_LAUNCHED_BEFORE, value).apply()

}