package com.rocket.charge.ui.preview

import com.rocket.charge.R
import com.rocket.charge.base.BaseDialog
import com.rocket.charge.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.buttonOk.setOnClickListener { onButtonClick() }
        binding.buttonClose.setOnClickListener { onButtonClick() }
    }

}