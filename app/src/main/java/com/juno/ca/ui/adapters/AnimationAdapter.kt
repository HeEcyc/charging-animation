package com.juno.ca.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.juno.ca.base.BaseAdapter
import com.juno.ca.databinding.ItemAnimationBinding
import com.juno.ca.model.animation.Animation
import com.juno.ca.model.animation.CustomAnimation
import com.juno.ca.model.animation.PresetAnimation

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
                    is PresetAnimation -> binding.preview.setImageResource(t.animation.previewPicRes)
                    is CustomAnimation -> Glide.with(binding.preview).load(t.animation.filePath).into(binding.preview)
                }
                binding.preview.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

    class AnimationViewModel(val animation: Animation)

}