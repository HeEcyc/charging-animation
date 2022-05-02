package com.smooth.battery.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.smooth.battery.base.BaseViewModel
import com.smooth.battery.ui.main.MainViewModel.MainFragment.*

class MainViewModel : BaseViewModel() {

    val showAnimation = MutableLiveData<Unit>()
    val showHome = MutableLiveData<Unit>()
    val showSettings = MutableLiveData<Unit>()

    val selectedFragment = ObservableField(HOME)

    fun onAnimationClick() {
        if (selectedFragment.get() == ANIMATION) return
        selectedFragment.set(ANIMATION)
        showAnimation.postValue(Unit)
    }

    fun onHomeClick() {
        if (selectedFragment.get() == HOME) return
        selectedFragment.set(HOME)
        showHome.postValue(Unit)
    }

    fun onSettingsClick() {
        if (selectedFragment.get() == SETTINGS) return
        selectedFragment.set(SETTINGS)
        showSettings.postValue(Unit)
    }

    enum class MainFragment { ANIMATION, HOME, SETTINGS }

}