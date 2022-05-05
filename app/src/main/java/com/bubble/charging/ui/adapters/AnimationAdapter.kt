package com.bubble.charging.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bubble.charging.base.BaseAdapter
import com.bubble.charging.databinding.ItemAnimationBinding
import com.bubble.charging.model.AnimationItem

class AnimationAdapter(
    onClick: (AnimationItem) -> Unit
) : BaseAdapter<AnimationItem, ItemAnimationBinding>(onClick) {

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
            }
        }
    }

}