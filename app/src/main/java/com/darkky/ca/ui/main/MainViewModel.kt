package com.darkky.ca.ui.main

import androidx.databinding.ObservableBoolean
import com.darkky.ca.base.BaseViewModel
import com.darkky.ca.model.AnimationItem
import com.darkky.ca.repository.preferences.Preferences
import com.darkky.ca.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapterPopular = AnimationAdapter()

    init {
        adapterPopular.reloadData(AnimationItem.values().toList())
    }

    fun onItemClick(item: AnimationItem) {
        Preferences.selectedAnimation = item
    }

    // Settings layout |
    //                 v
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

}