package com.kapi.ca.ui.animations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kapi.ca.App
import com.kapi.ca.base.BaseAdapter
import com.kapi.ca.databinding.ItemAnimationBinding
import com.kapi.ca.model.animation.Animation
import com.kapi.ca.model.animation.CustomAnimation
import com.kapi.ca.model.animation.PresetAnimation

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