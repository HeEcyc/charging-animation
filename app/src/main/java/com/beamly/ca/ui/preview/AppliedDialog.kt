package com.beamly.ca.ui.preview

import com.beamly.ca.R
import com.beamly.ca.base.BaseDialog
import com.beamly.ca.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.buttonOk.setOnClickListener { onButtonClick() }
        binding.buttonClose.setOnClickListener { onButtonClick() }
    }

}