package com.urbanlife.lifeurban.ui.animations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.urbanlife.lifeurban.App
import com.urbanlife.lifeurban.base.BaseAdapter
import com.urbanlife.lifeurban.databinding.ItemAnimationBinding
import com.urbanlife.lifeurban.model.animation.Animation
import com.urbanlife.lifeurban.model.animation.CustomAnimation
import com.urbanlife.lifeurban.model.animation.PresetAnimation

class AnimationAdapter(
    onClick: (Animation) -> Unit
) : BaseAdapter<Animation, ItemAnimationBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding) =
        object : BaseItem<Animation, ItemAnimationBinding>(binding) {
            override fun bind(t: Animation) {
                when (t) {
                    is PresetAnimation -> binding.image.setImageResource(t.previewPicRes)
                    is CustomAnimation -> Glide.with(App.instance).load(t.filePath).into(binding.image)
                }
            }
        }

}