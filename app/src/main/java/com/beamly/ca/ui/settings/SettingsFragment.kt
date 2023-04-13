package com.beamly.ca.ui.settings

import androidx.fragment.app.viewModels
import com.beamly.ca.R
import com.beamly.ca.base.BaseFragment
import com.beamly.ca.databinding.SettingsFargementBinding
import com.beamly.ca.ui.main.MainActivity

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener {
            activityAs<MainActivity>().viewModel.onHomeClick()
        }
    }

    override fun provideViewModel() = viewModel

}