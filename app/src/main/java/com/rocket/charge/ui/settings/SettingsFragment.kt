package com.rocket.charge.ui.settings

import androidx.fragment.app.viewModels
import com.rocket.charge.R
import com.rocket.charge.base.BaseFragment
import com.rocket.charge.databinding.SettingsFargementBinding

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}