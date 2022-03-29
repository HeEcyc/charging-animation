package com.ioscharginging.iosanimationation.ui.main

import com.ioscharginging.iosanimationation.base.BaseViewModel
import com.ioscharginging.iosanimationation.model.AnimationItem
import com.ioscharginging.iosanimationation.repository.preferences.Preferences
import com.ioscharginging.iosanimationation.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapter = AnimationAdapter {
        Preferences.selectedAnimation = it
    }

    init {
        adapter.reloadData(AnimationItem.values().toList())
    }

}