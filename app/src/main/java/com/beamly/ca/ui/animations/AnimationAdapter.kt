package com.beamly.ca.ui.animations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.beamly.ca.App
import com.beamly.ca.base.BaseAdapter
import com.beamly.ca.databinding.ItemAnimationBinding
import com.beamly.ca.model.animation.Animation
import com.beamly.ca.model.animation.CustomAnimation
import com.beamly.ca.model.animation.PresetAnimation

class AnimationAdapter(
    onClick: (Animation) -> Unit
) : BaseAdapter<Animation, ItemAnimationBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding) =
        object : BaseItem<Animation, ItemAnimationBinding>(binding) {
            override fun bind(t: Animation) {
                when (t) {
                    is PresetAnimation -> binding.image.setImageResource(t.previewPicRes)
                    is CustomAnimation -> Glide.with(App.instance).load(t.filePath).into(binding.image)
                }
            }
        }

}