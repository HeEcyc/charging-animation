package com.happy.charger.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.happy.charger.base.BaseAdapter
import com.happy.charger.databinding.ItemAnimationBinding
import com.happy.charger.model.AnimationItem

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