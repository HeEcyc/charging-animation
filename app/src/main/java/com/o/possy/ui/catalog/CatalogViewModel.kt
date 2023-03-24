package com.o.possy.ui.catalog

import androidx.lifecycle.MutableLiveData
import com.o.possy.base.BaseViewModel
import com.o.possy.model.animation.Animation
import com.o.possy.model.animation.PresetAnimation
import com.o.possy.ui.adapters.AnimationAdapter

class CatalogViewModel : BaseViewModel() {

    val showPreview = MutableLiveData<Animation>()
    val adapter = AnimationAdapter(::onItemClick)

    init {
        adapter.reloadData(PresetAnimation.values().mapIndexed { _, item ->
            AnimationAdapter.AnimationViewModel(item)
        })
    }

    private fun onItemClick(avm: AnimationAdapter.AnimationViewModel) {
        showPreview.postValue(avm.animation)
    }

}