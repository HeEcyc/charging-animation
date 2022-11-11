package com.darkky.ca.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.darkky.ca.base.BaseAdapter
import com.darkky.ca.databinding.ItemAnimationBinding
import com.darkky.ca.model.animation.Animation
import com.darkky.ca.model.animation.CustomAnimation
import com.darkky.ca.model.animation.PresetAnimation

class AnimationAdapter : BaseAdapter<Animation, ItemAnimationBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding): BaseItem<Animation, ItemAnimationBinding> {
        return object : BaseItem<Animation, ItemAnimationBinding>(binding) {
            override fun bind(t: Animation) {
                super.bind(t)
                when (t) {
                    is PresetAnimation -> binding.preview.setImageResource(t.previewPicRes)
                    is CustomAnimation -> Glide.with(binding.preview).load(t.filePath).into(binding.preview)
                }
                binding.preview.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

}