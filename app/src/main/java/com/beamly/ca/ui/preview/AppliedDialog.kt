package com.beamly.ca.ui.preview

import androidx.core.view.postDelayed
import com.beamly.ca.R
import com.beamly.ca.base.BaseDialog
import com.beamly.ca.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.root.setOnClickListener { onButtonClick() }
        binding.root.postDelayed(2000) { onButtonClick() }
    }

}