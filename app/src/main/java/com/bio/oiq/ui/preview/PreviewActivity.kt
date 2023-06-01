package com.bio.oiq.ui.preview

import android.view.View
import androidx.core.view.postDelayed
import com.bio.oiq.R
import com.bio.oiq.base.BaseDialog
import com.bio.oiq.databinding.PreviewActivityBinding
import com.bio.oiq.model.animation.Animation
import com.bio.oiq.model.animation.CustomAnimation
import com.bio.oiq.model.animation.PresetAnimation
import com.bio.oiq.repository.preferences.Preferences
import com.bumptech.glide.Glide

class PreviewActivity : BaseDialog<PreviewActivityBinding>(R.layout.preview_activity) {

    var animation: Animation? = null

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { dismiss() }
        val animation = animation
        if (animation === null) return
        if (animation is PresetAnimation) {
            binding.preview.setImageResource(animation.previewPicRes)
        } else if (animation is CustomAnimation) {
            Glide.with(binding.preview).load(animation.filePath).into(binding.preview)
        }
        binding.animationContainerSmall.addView(animation.inflateAnimationView(requireContext()))
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            binding.applied.visibility = View.VISIBLE
            it.postDelayed(2000, ::dismiss)
        }
    }

}