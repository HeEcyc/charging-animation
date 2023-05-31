package com.bio.oiq.ui.settings

import androidx.databinding.ObservableBoolean
import com.bio.oiq.base.BaseViewModel
import com.bio.oiq.repository.preferences.Preferences

class SettingsViewModel : BaseViewModel() {

    val isAnimationOn = ObservableBoolean(Preferences.showWhenUnlocked)
    val areNotificationsOn = ObservableBoolean(Preferences.areNotificationsOn)
    val isFlashOn = ObservableBoolean(Preferences.isFlashOn)
    val isVibrationOn = ObservableBoolean(Preferences.isVibrationOn)
    val isSoundOn = ObservableBoolean(Preferences.isSoundOn)

    init {
        observe(areNotificationsOn) { _, _ -> Preferences.areNotificationsOn = areNotificationsOn.get() }
        observe(isFlashOn) { _, _ -> Preferences.isFlashOn = isFlashOn.get() }
        observe(isVibrationOn) { _, _ -> Preferences.isVibrationOn = isVibrationOn.get() }
        observe(isSoundOn) { _, _ -> Preferences.isSoundOn = isSoundOn.get() }
    }

    fun onTurnOnOffClick() {
        val newValue = !Preferences.showWhenUnlocked
        Preferences.showWhenUnlocked = newValue
        isAnimationOn.set(newValue)
    }

    fun onNotificationsClick() {
        areNotificationsOn.set(!areNotificationsOn.get())
        isFlashOn.set(isFlashOn.get() && areNotificationsOn.get())
        isVibrationOn.set(isVibrationOn.get() && areNotificationsOn.get())
        isSoundOn.set(isSoundOn.get() && areNotificationsOn.get())
    }

    private fun refreshNotifications() =
        areNotificationsOn.set(areNotificationsOn.get() || isFlashOn.get() || isVibrationOn.get() || isSoundOn.get())

    fun onFlashClick() {
        isFlashOn.set(!isFlashOn.get())
        refreshNotifications()
    }

    fun onVibrationClick() {
        isVibrationOn.set(!isVibrationOn.get())
        refreshNotifications()
    }

    fun onSoundClick() {
        isSoundOn.set(!isSoundOn.get())
        refreshNotifications()
    }

}