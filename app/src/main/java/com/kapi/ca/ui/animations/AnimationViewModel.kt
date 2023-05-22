package com.kapi.ca.ui.animations

import androidx.lifecycle.MutableLiveData
import com.kapi.ca.base.BaseViewModel
import com.kapi.ca.model.animation.Animation
import com.kapi.ca.model.animation.PresetAnimation

class AnimationViewModel : BaseViewModel() {

    val showPreview = MutableLiveData<Animation>()

    val adapterAnimation = AnimationAdapter(::onAnimationClick)

    init {
        adapterAnimation.reloadData(PresetAnimation.values().toList())
    }

    private fun onAnimationClick(animation: Animation) = showPreview.postValue(animation)

}