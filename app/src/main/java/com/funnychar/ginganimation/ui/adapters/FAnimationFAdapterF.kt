package com.funnychar.ginganimation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.funnychar.ginganimation.base.FBaseFAdapterF
import com.funnychar.ginganimation.databinding.ItemAnimationBinding
import com.funnychar.ginganimation.model.FAnimationFItemF

class FAnimationFAdapterF : FBaseFAdapterF<FAnimationFItemF, ItemAnimationBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAnimationBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAnimationBinding): BaseItem<FAnimationFItemF, ItemAnimationBinding> {
        return object : BaseItem<FAnimationFItemF, ItemAnimationBinding>(binding) {
            override fun bind(t: FAnimationFItemF) {
                System.currentTimeMillis()
                super.bind(t)
                System.currentTimeMillis()
                binding.preview.setImageResource(t.previewPicRes)
                System.currentTimeMillis()
                binding.preview.scaleType = ImageView.ScaleType.CENTER_CROP
                System.currentTimeMillis()
            }
        }
    }

}