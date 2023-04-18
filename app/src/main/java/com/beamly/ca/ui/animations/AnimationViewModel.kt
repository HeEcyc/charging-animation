package com.beamly.ca.ui.animations

import androidx.lifecycle.MutableLiveData
import com.beamly.ca.base.BaseViewModel
import com.beamly.ca.model.animation.Animation
import com.beamly.ca.model.animation.PresetAnimation

class AnimationViewModel : BaseViewModel() {

    val showPreview = MutableLiveData<Animation>()

    val adapterAnimation = AnimationAdapter(::onAnimationClick)

    init {
        adapterAnimation.reloadData(PresetAnimation.values().toList())
    }

    private fun onAnimationClick(animation: Animation) = showPreview.postValue(animation)

}