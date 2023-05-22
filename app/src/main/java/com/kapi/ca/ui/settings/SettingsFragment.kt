package com.kapi.ca.ui.settings

import androidx.fragment.app.viewModels
import com.kapi.ca.R
import com.kapi.ca.base.BaseFragment
import com.kapi.ca.databinding.SettingsFargementBinding

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}