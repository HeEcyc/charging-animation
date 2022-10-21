package com.funnychar.ginganimation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.funnychar.ginganimation.base.BaseAdapter
import com.funnychar.ginganimation.databinding.ItemAnimationBinding
import com.funnychar.ginganimation.model.AnimationItem

class AnimationAdapter : BaseAdapter<AnimationItem, ItemAnimationBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding): BaseItem<AnimationItem, ItemAnimationBinding> {
        return object : BaseItem<AnimationItem, ItemAnimationBinding>(binding) {
            override fun bind(t: AnimationItem) {
                super.bind(t)
                binding.preview.setImageResource(t.previewPicRes)
                binding.preview.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

}