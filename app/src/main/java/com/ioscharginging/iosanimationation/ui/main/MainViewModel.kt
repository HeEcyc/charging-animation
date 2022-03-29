package com.ioscharginging.iosanimationation.ui.main

import androidx.lifecycle.MutableLiveData
import com.ioscharginging.iosanimationation.base.BaseViewModel
import com.ioscharginging.iosanimationation.model.AnimationItem
import com.ioscharginging.iosanimationation.repository.preferences.Preferences
import com.ioscharginging.iosanimationation.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val selectedItem = MutableLiveData<AnimationItem>()

    val adapterPopular = AnimationAdapter(::onItemClick)
    val adapterOther = AnimationAdapter(::onItemClick)
    private val allItems: List<AnimationAdapter.AnimationViewModel>

    init {
        selectedItem.postValue(Preferences.selectedAnimation)
        adapterPopular.reloadData(AnimationItem.valuesPopular.map { AnimationAdapter.AnimationViewModel(it) })
        adapterOther.reloadData(AnimationItem.valuesOther.map { AnimationAdapter.AnimationViewModel(it) })
        allItems = listOf(
            *adapterPopular.getData().toTypedArray(),
            *adapterOther.getData().toTypedArray()
        )
        allItems.first {
            val isCurrent = it.animation == Preferences.selectedAnimation
            if (isCurrent) it.isSelected.set(true)
            isCurrent
        }
    }

    private fun onItemClick(item: AnimationAdapter.AnimationViewModel) {
        allItems.forEach { it.isSelected.set(it.animation == item.animation) }
        if (selectedItem.value != item.animation) {
            selectedItem.postValue(item.animation)
        }
    }

    fun apply() {
        Preferences.selectedAnimation = selectedItem.value ?: Preferences.selectedAnimation
    }

}