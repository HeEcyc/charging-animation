package com.happy.charger.ui.main

import com.happy.charger.base.BaseViewModel
import com.happy.charger.model.AnimationItem
import com.happy.charger.repository.preferences.Preferences
import com.happy.charger.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val adapterPopular = AnimationAdapter(::onItemClick)

    init {
        adapterPopular.reloadData(AnimationItem.values().toList())
    }

    private fun onItemClick(item: AnimationItem) {
        Preferences.selectedAnimation = item
    }

}