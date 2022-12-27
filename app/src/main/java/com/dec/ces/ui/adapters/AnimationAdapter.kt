package com.dec.ces.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dec.ces.base.BaseAdapter
import com.dec.ces.databinding.ItemAnimationBinding
import com.dec.ces.model.animation.Animation
import com.dec.ces.model.animation.CustomAnimation
import com.dec.ces.model.animation.PresetAnimation

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