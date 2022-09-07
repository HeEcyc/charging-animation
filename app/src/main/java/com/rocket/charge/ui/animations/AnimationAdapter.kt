package com.rocket.charge.ui.animations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rocket.charge.App
import com.rocket.charge.base.BaseAdapter
import com.rocket.charge.databinding.ItemAnimationBinding
import com.rocket.charge.model.animation.Animation
import com.rocket.charge.model.animation.CustomAnimation
import com.rocket.charge.model.animation.PresetAnimation

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