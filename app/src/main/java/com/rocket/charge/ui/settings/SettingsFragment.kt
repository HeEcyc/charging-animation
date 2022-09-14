package com.rocket.charge.ui.settings

import androidx.fragment.app.viewModels
import com.rocket.charge.R
import com.rocket.charge.base.BaseFragment
import com.rocket.charge.databinding.SettingsFargementBinding
import com.rocket.charge.ui.main.MainActivity

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener {
            activityAs<MainActivity>().viewModel.onHomeClick()
        }
    }

    override fun provideViewModel() = viewModel

}