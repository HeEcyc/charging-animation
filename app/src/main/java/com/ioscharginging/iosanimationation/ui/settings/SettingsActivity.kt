package com.ioscharginging.iosanimationation.ui.settings

import androidx.activity.viewModels
import com.ioscharginging.iosanimationation.R
import com.ioscharginging.iosanimationation.base.BaseActivity
import com.ioscharginging.iosanimationation.databinding.SettingsActivityBinding
import com.ioscharginging.iosanimationation.utils.APP_LINK

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