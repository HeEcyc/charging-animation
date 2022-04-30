package com.smooth.battery.ui.main

import com.smooth.battery.base.BaseViewModel
import com.smooth.battery.model.AnimationItem
import com.smooth.battery.repository.preferences.Preferences
import com.smooth.battery.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapterPopular = AnimationAdapter(::onItemClick)

    init {
        adapterPopular.reloadData(AnimationItem.values().toList())
    }

    private fun onItemClick(item: AnimationItem) {
        Preferences.selectedAnimation = item
    }

}