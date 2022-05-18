package com.funny.charging_app.ui.main

import com.funny.charging_app.base.BaseViewModel
import com.funny.charging_app.model.AnimationItem
import com.funny.charging_app.repository.preferences.Preferences
import com.funny.charging_app.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapterPopular = AnimationAdapter(::onItemClick)

    init {
        adapterPopular.reloadData(AnimationItem.values().toList())
    }

    private fun onItemClick(item: AnimationItem) {
        Preferences.selectedAnimation = item
    }

}