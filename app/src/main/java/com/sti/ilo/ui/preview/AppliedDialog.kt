package com.sti.ilo.ui.preview

import androidx.core.view.postDelayed
import com.sti.ilo.R
import com.sti.ilo.base.BaseDialog
import com.sti.ilo.databinding.AppliedDialogBinding

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.root.postDelayed(2000) {
            requireActivity().finish()
        }
    }

}