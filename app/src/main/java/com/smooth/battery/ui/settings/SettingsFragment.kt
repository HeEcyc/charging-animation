package com.smooth.battery.ui.settings

import androidx.fragment.app.viewModels
import com.smooth.battery.R
import com.smooth.battery.base.BaseFragment
import com.smooth.battery.databinding.SettingsFargementBinding
import com.smooth.battery.utils.APP_LINK

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonShare.setOnClickListener { showSharedIntent() }
        binding.buttonRateUs.setOnClickListener { openLink(APP_LINK) }
        binding.buttonPrivacyPolicy.setOnClickListener { openLink(APP_LINK) }
    }

    override fun provideViewModel() = viewModel

}