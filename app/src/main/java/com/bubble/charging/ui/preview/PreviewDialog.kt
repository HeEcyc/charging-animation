package com.bubble.charging.ui.preview

import androidx.constraintlayout.widget.ConstraintLayout
import com.bubble.charging.R
import com.bubble.charging.base.BaseDialog
import com.bubble.charging.databinding.PreviewDialogBinding
import com.bubble.charging.model.AnimationItem
import com.bubble.charging.repository.preferences.Preferences

class PreviewDialog : BaseDialog<PreviewDialogBinding>(R.layout.preview_dialog) {

    var animation: AnimationItem? = null

    override fun setupUI() {
        val a = animation
        if (a !== null) {
            binding.animationContainer.addView(
                a.inflateAnimationView(requireContext()),
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            binding.buttonClose.setOnClickListener { dismiss() }
            binding.buttonApply.setOnClickListener {
                Preferences.selectedAnimation = a
                dismiss()
            }
        } else dismiss()
    }

}