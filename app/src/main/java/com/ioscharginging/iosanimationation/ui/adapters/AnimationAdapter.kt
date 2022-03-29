package com.ioscharginging.iosanimationation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.ioscharginging.iosanimationation.base.BaseAdapter
import com.ioscharginging.iosanimationation.databinding.ItemAnimationBinding
import com.ioscharginging.iosanimationation.model.AnimationItem

class AnimationAdapter(
    onClick: (AnimationViewModel) -> Unit
) : BaseAdapter<AnimationAdapter.AnimationViewModel, ItemAnimationBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding): BaseItem<AnimationViewModel, ItemAnimationBinding> {
        return object : BaseItem<AnimationViewModel, ItemAnimationBinding>(binding) {
            override fun bind(t: AnimationViewModel) {
                super.bind(t)
                binding.preview.setImageResource(t.animation.previewPicRes)
            }
        }
    }

    class AnimationViewModel(val animation: AnimationItem) {
        val isSelected = ObservableBoolean(false)
    }

}