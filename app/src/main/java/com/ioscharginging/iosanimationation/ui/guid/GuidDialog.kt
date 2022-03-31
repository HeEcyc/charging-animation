package com.ioscharginging.iosanimationation.ui.guid

import com.ioscharginging.iosanimationation.R
import com.ioscharginging.iosanimationation.base.BaseDialog
import com.ioscharginging.iosanimationation.databinding.GuidDialogBinding
import com.ioscharginging.iosanimationation.repository.preferences.Preferences

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {

    override fun setupUI() {
        Preferences.wasLaunchedBefore = true
        binding.buttonOk.setOnClickListener { dismiss() }
    }

}