package com.ioscharginging.iosanimationation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ioscharginging.iosanimationation.base.BaseAdapter
import com.ioscharginging.iosanimationation.databinding.ItemAnimationBinding
import com.ioscharginging.iosanimationation.model.AnimationItem

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