package com.smooth.battery.ui.settings

import androidx.activity.viewModels
import com.smooth.battery.R
import com.smooth.battery.base.BaseActivity
import com.smooth.battery.databinding.SettingsActivityBinding
import com.smooth.battery.utils.APP_LINK

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonShare.setOnClickListener { showSharedIntent() }
        binding.buttonRateUs.setOnClickListener { openLink(APP_LINK) }
        binding.buttonPrivacyPolicy.setOnClickListener { openLink(APP_LINK) }
    }

}