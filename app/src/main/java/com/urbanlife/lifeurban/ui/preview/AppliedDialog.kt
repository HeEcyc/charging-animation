package com.urbanlife.lifeurban.ui.preview

import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseDialog
import com.urbanlife.lifeurban.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.buttonOk.setOnClickListener { onButtonClick() }
        binding.buttonClose.setOnClickListener { onButtonClick() }
    }

}