package com.funny.charging_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funny.charging_app.base.BaseAdapter
import com.funny.charging_app.databinding.ItemAnimationBinding
import com.funny.charging_app.model.AnimationItem

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