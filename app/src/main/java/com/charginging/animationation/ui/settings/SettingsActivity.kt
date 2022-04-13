package com.charginging.animationation.ui.settings

import com.charginging.animationation.R
import com.charginging.animationation.base.BaseActivity
import com.charginging.animationation.databinding.SettingsActivityBinding
import com.charginging.animationation.utils.APP_LINK

class SettingsActivity : BaseActivity<SettingsActivityBinding>(R.layout.settings_activity) {

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonShare.setOnClickListener { showSharedIntent() }
        binding.buttonRateUs.setOnClickListener { openLink(APP_LINK) }
        binding.buttonPrivacyPolicy.setOnClickListener { openLink(APP_LINK) }
    }

}