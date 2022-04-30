package com.smooth.battery.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smooth.battery.base.BaseAdapter
import com.smooth.battery.databinding.ItemAnimationBinding
import com.smooth.battery.model.AnimationItem

class AnimationAdapter(
    private val onClick: (AnimationItem) -> Unit
) : BaseAdapter<AnimationItem, ItemAnimationBinding>() {

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
                binding.button.setOnClickListener { onClick(t) }
            }
        }
    }

}