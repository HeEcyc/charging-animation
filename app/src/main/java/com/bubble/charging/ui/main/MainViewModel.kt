package com.bubble.charging.ui.main

import com.bubble.charging.base.BaseViewModel
import com.bubble.charging.model.AnimationItem
import com.bubble.charging.repository.preferences.Preferences
import com.bubble.charging.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapter = AnimationAdapter {
        Preferences.selectedAnimation = it
    }

    init {
        adapter.reloadData(AnimationItem.values().toList())
    }

}