package com.charginging.animationation.ui.main

import com.charginging.animationation.base.BaseViewModel
import com.charginging.animationation.model.AnimationItem
import com.charginging.animationation.repository.preferences.Preferences
import com.charginging.animationation.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapter = AnimationAdapter {
        Preferences.selectedAnimation = it
    }

    init {
        adapter.reloadData(AnimationItem.values().toList())
    }

}