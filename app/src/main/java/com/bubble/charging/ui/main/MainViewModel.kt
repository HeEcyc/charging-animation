package com.bubble.charging.ui.main

import androidx.lifecycle.MutableLiveData
import com.bubble.charging.base.BaseViewModel
import com.bubble.charging.model.AnimationItem
import com.bubble.charging.repository.preferences.Preferences
import com.bubble.charging.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val onItemClick = MutableLiveData<AnimationItem>()

    val adapter = AnimationAdapter(onItemClick::postValue)

    init {
        adapter.reloadData(AnimationItem.values().toList())
    }

}