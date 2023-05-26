package com.sti.ilo.ui.preview

import com.sti.ilo.R
import com.sti.ilo.base.BaseDialog
import com.sti.ilo.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.buttonOk.setOnClickListener { onButtonClick() }
        binding.buttonClose.setOnClickListener { onButtonClick() }
    }

}