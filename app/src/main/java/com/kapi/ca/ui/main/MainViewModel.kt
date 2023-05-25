package com.kapi.ca.ui.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.kapi.ca.base.BaseViewModel
import com.kapi.ca.repository.preferences.Preferences
import com.kapi.ca.ui.main.MainViewModel.MainFragment.ANIMATION
import com.kapi.ca.ui.main.MainViewModel.MainFragment.SETTINGS

class MainViewModel : BaseViewModel() {

    val selectedFragment = ObservableField(ANIMATION)

    val isAppOn = ObservableBoolean(Preferences.showWhenUnlocked)
    val areNotificationsOn = ObservableBoolean(Preferences.areNotificationsOn)
    val isFlashOn = ObservableBoolean(Preferences.isFlashOn)
    val isVibrationOn = ObservableBoolean(Preferences.isVibrationOn)
    val isSoundOn = ObservableBoolean(Preferences.isSoundOn)

    init {
        observe(isAppOn) { _, _ -> Preferences.showWhenUnlocked = isAppOn.get() }
        observe(areNotificationsOn) { _, _ -> Preferences.areNotificationsOn = areNotificationsOn.get() }
        observe(isFlashOn) { _, _ -> Preferences.isFlashOn = isFlashOn.get() }
        observe(isVibrationOn) { _, _ -> Preferences.isVibrationOn = isVibrationOn.get() }
        observe(isSoundOn) { _, _ -> Preferences.isSoundOn = isSoundOn.get() }
    }

    fun onTurnOnOffClick() = isAppOn.set(!isAppOn.get())

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

    fun onAnimationClick() {
        if (selectedFragment.get() == ANIMATION) return
        selectedFragment.set(ANIMATION)
    }

    fun onSettingsClick() {
        if (selectedFragment.get() == SETTINGS) return
        selectedFragment.set(SETTINGS)
    }

    enum class MainFragment { ANIMATION, SETTINGS }

}