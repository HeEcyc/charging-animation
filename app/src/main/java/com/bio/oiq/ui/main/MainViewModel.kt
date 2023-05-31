package com.bio.oiq.ui.main

import androidx.lifecycle.MutableLiveData
import com.bio.oiq.base.BaseViewModel
import com.bio.oiq.model.animation.Animation
import com.bio.oiq.model.animation.PresetAnimation
import com.bio.oiq.ui.adapters.AnimationAdapter

class MainViewModel : BaseViewModel() {

    val showPreview = MutableLiveData<Animation>()
    val adapter = AnimationAdapter(::onItemClick)

    init {
        adapter.reloadData(PresetAnimation.values().mapIndexed { i, item ->
            AnimationAdapter.AnimationViewModel(item).apply { isSelected.set(i == 0) }
        })
    }

    var selectedAnimation = adapter.getData().first()

    private fun onItemClick(avm: AnimationAdapter.AnimationViewModel) {
        selectedAnimation = avm
        adapter.getData().forEach { it.isSelected.set(it === avm) }
    }

    fun onApplyClick() = showPreview.postValue(selectedAnimation.animation)

}