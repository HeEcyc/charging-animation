package com.smooth.battery.ui.guid

import com.smooth.battery.R
import com.smooth.battery.base.BaseDialog
import com.smooth.battery.databinding.GuidDialogBinding
import com.smooth.battery.repository.preferences.Preferences

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {

    override fun setupUI() {
        Preferences.wasLaunchedBefore = true
        binding.buttonOk.setOnClickListener { dismiss() }
    }

}