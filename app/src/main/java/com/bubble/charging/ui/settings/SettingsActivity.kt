package com.bubble.charging.ui.settings

import androidx.activity.viewModels
import com.bubble.charging.R
import com.bubble.charging.base.BaseActivity
import com.bubble.charging.databinding.SettingsActivityBinding
import com.bubble.charging.utils.APP_LINK

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