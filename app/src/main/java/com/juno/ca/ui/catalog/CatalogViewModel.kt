package com.juno.ca.ui.catalog

import androidx.lifecycle.MutableLiveData
import com.juno.ca.base.BaseViewModel
import com.juno.ca.model.animation.Animation
import com.juno.ca.model.animation.PresetAnimation
import com.juno.ca.ui.adapters.AnimationAdapter

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