package com.funny.charging_app.ui.guid

import com.funny.charging_app.R
import com.funny.charging_app.base.BaseDialog
import com.funny.charging_app.databinding.GuidDialogBinding
import com.funny.charging_app.repository.preferences.Preferences

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {

    override fun setupUI() {
        Preferences.wasLaunchedBefore = true
        binding.buttonOk.setOnClickListener { dismiss() }
    }

}