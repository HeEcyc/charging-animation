package com.happy.charger.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.happy.charger.base.BaseAdapter
import com.happy.charger.databinding.ItemAnimationBinding
import com.happy.charger.model.AnimationItem

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