package com.bio.oiq.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.bio.oiq.base.BaseAdapter
import com.bio.oiq.databinding.ItemAnimationBinding
import com.bio.oiq.model.animation.Animation
import com.bio.oiq.model.animation.CustomAnimation
import com.bio.oiq.model.animation.PresetAnimation

class AnimationAdapter(
    private val onClick: (AnimationViewModel) -> Unit
) : BaseAdapter<AnimationAdapter.AnimationViewModel, ItemAnimationBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding): BaseItem<AnimationViewModel, ItemAnimationBinding> {
        return object : BaseItem<AnimationViewModel, ItemAnimationBinding>(binding) {
            override fun bind(t: AnimationViewModel) {
                super.bind(t)
                binding.root.setOnClickListener { onClick(t) }
                when (t.animation) {
                    is PresetAnimation -> {
                        binding.preview1.setImageResource(t.animation.previewPicRes)
                        binding.preview2.setImageResource(t.animation.previewPicRes)
                    }
                    is CustomAnimation -> {
                        Glide.with(binding.preview1).load(t.animation.filePath).into(binding.preview1)
                        Glide.with(binding.preview2).load(t.animation.filePath).into(binding.preview2)
                    }
                }
                binding.preview1.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.preview2.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

    class AnimationViewModel(val animation: Animation) {
        val isSelected = ObservableBoolean(false)
    }

}