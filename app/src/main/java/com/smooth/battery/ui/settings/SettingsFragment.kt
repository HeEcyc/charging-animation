package com.smooth.battery.ui.settings

import androidx.fragment.app.viewModels
import com.smooth.battery.R
import com.smooth.battery.base.BaseFragment
import com.smooth.battery.databinding.SettingsFargementBinding

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}