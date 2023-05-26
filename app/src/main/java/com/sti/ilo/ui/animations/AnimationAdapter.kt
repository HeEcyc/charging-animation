package com.sti.ilo.ui.animations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sti.ilo.App
import com.sti.ilo.base.BaseAdapter
import com.sti.ilo.databinding.ItemAnimationBinding
import com.sti.ilo.model.animation.Animation
import com.sti.ilo.model.animation.CustomAnimation
import com.sti.ilo.model.animation.PresetAnimation

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
                binding.image2.visibility = if (bindingAdapterPosition == 0) View.GONE else View.VISIBLE
                when (t) {
                    is PresetAnimation -> {
                        binding.image1.setImageResource(t.previewPicRes)
                        binding.image2.setImageResource(t.previewPicRes)
                    }
                    is CustomAnimation -> {
                        Glide.with(App.instance).load(t.filePath).into(binding.image1)
                        Glide.with(App.instance).load(t.filePath).into(binding.image2)
                    }
                }
            }
        }

}