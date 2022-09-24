package com.charful.cheerge.ui.preview

import com.charful.cheerge.R
import com.charful.cheerge.base.BaseDialog
import com.charful.cheerge.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.buttonOk.setOnClickListener { onButtonClick() }
        binding.buttonClose.setOnClickListener { onButtonClick() }
    }

}