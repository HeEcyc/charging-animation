package com.happy.charger.ui.guid

import com.happy.charger.R
import com.happy.charger.base.BaseDialog
import com.happy.charger.databinding.GuidDialogBinding
import com.happy.charger.repository.preferences.Preferences

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {

    override fun setupUI() {
        Preferences.wasLaunchedBefore = true
        binding.buttonOk.setOnClickListener { dismiss() }
    }

}