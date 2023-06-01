package com.bio.oiq.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.bio.oiq.base.BaseViewModel
import com.bio.oiq.model.animation.Animation
import com.bio.oiq.model.animation.PresetAnimation
import com.bio.oiq.repository.preferences.Preferences
import com.bio.oiq.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val showPreview = MutableLiveData<Animation>()
    val adapter = AnimationAdapter(::onItemClick)

    val isAppOn = ObservableBoolean(Preferences.showWhenUnlocked)
    val areNotificationsOn = ObservableBoolean(Preferences.areNotificationsOn)
    val isFlashOn = ObservableBoolean(Preferences.isFlashOn)
    val isVibrationOn = ObservableBoolean(Preferences.isVibrationOn)
    val isSoundOn = ObservableBoolean(Preferences.isSoundOn)

    init {
        adapter.reloadData(PresetAnimation.values().mapIndexed { i, item ->
            AnimationAdapter.AnimationViewModel(item).apply { isSelected.set(i == 0) }
        })
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

    var selectedAnimation = adapter.getData().first()

    private fun onItemClick(avm: AnimationAdapter.AnimationViewModel) {
        selectedAnimation = avm
        adapter.getData().forEach { it.isSelected.set(it === avm) }
        onApplyClick()
    }

    fun onApplyClick() = showPreview.postValue(selectedAnimation.animation)

}