package com.kapi.ca.ui.preview

import androidx.core.view.postDelayed
import com.kapi.ca.R
import com.kapi.ca.base.BaseDialog
import com.kapi.ca.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    var onButtonClick: () -> Unit = {}

    override fun setupUI() {
        binding.root.setOnClickListener { onButtonClick() }
        binding.root.postDelayed(2000) { onButtonClick() }
    }

}