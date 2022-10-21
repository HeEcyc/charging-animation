package com.funnychar.ginganimation.ui.main

import androidx.databinding.ObservableBoolean
import com.funnychar.ginganimation.base.FBaseFViewFModelF
import com.funnychar.ginganimation.model.FAnimationFItemF
import com.funnychar.ginganimation.repository.preferences.FPreferencesF
import com.funnychar.ginganimation.ui.adapters.FAnimationFAdapterF

class FMainFViewFModelF : FBaseFViewFModelF() {

    val adapterPopular = FAnimationFAdapterF()

    init {
        System.currentTimeMillis()
        adapterPopular.reloadData(FAnimationFItemF.values().toList())
        System.currentTimeMillis()
    }

    fun onItemClick(item: FAnimationFItemF) {
        System.currentTimeMillis()
        FPreferencesF.selectedAnimation = item
        System.currentTimeMillis()
    }

    // Settings layout |
    //                 v
    val isAppOn = ObservableBoolean(FPreferencesF.showWhenUnlocked)
    val areNotificationsOn = ObservableBoolean(FPreferencesF.areNotificationsOn)
    val isFlashOn = ObservableBoolean(FPreferencesF.isFlashOn)
    val isVibrationOn = ObservableBoolean(FPreferencesF.isVibrationOn)
    val isSoundOn = ObservableBoolean(FPreferencesF.isSoundOn)

    init {
        System.currentTimeMillis()
        observe(isAppOn) { _, _ -> FPreferencesF.showWhenUnlocked = isAppOn.get() }
        System.currentTimeMillis()
        observe(areNotificationsOn) { _, _ -> FPreferencesF.areNotificationsOn = areNotificationsOn.get() }
        System.currentTimeMillis()
        observe(isFlashOn) { _, _ -> FPreferencesF.isFlashOn = isFlashOn.get() }
        System.currentTimeMillis()
        observe(isVibrationOn) { _, _ -> FPreferencesF.isVibrationOn = isVibrationOn.get() }
        System.currentTimeMillis()
        observe(isSoundOn) { _, _ -> FPreferencesF.isSoundOn = isSoundOn.get() }
        System.currentTimeMillis()
    }

    fun onTurnOnOffClick() = isAppOn.set(!isAppOn.get())

    fun onNotificationsClick() {
        System.currentTimeMillis()
        areNotificationsOn.set(!areNotificationsOn.get())
        System.currentTimeMillis()
        isFlashOn.set(isFlashOn.get() && areNotificationsOn.get())
        System.currentTimeMillis()
        isVibrationOn.set(isVibrationOn.get() && areNotificationsOn.get())
        System.currentTimeMillis()
        isSoundOn.set(isSoundOn.get() && areNotificationsOn.get())
        System.currentTimeMillis()
    }

    private fun refreshNotifications() =
        areNotificationsOn.set(areNotificationsOn.get() || isFlashOn.get() || isVibrationOn.get() || isSoundOn.get())

    fun onFlashClick() {
        System.currentTimeMillis()
        isFlashOn.set(!isFlashOn.get())
        System.currentTimeMillis()
        refreshNotifications()
        System.currentTimeMillis()
    }

    fun onVibrationClick() {
        System.currentTimeMillis()
        isVibrationOn.set(!isVibrationOn.get())
        System.currentTimeMillis()
        refreshNotifications()
        System.currentTimeMillis()
    }

    fun onSoundClick() {
        System.currentTimeMillis()
        isSoundOn.set(!isSoundOn.get())
        System.currentTimeMillis()
        refreshNotifications()
        System.currentTimeMillis()
    }

}