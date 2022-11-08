package com.darkky.ca.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.darkky.ca.base.BaseAdapter
import com.darkky.ca.databinding.ItemAnimationBinding
import com.darkky.ca.model.AnimationItem

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