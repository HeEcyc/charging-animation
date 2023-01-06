package com.dec.ces.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.dec.ces.base.BaseAdapter
import com.dec.ces.databinding.ItemAnimationBinding
import com.dec.ces.model.animation.Animation
import com.dec.ces.model.animation.CustomAnimation
import com.dec.ces.model.animation.PresetAnimation

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

    class AnimationViewModel(val animation: Animation) {
        val isSelected = ObservableBoolean(false)
    }

}