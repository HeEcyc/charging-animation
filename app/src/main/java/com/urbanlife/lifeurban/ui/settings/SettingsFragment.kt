package com.urbanlife.lifeurban.ui.settings

import androidx.fragment.app.viewModels
import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseFragment
import com.urbanlife.lifeurban.databinding.SettingsFargementBinding
import com.urbanlife.lifeurban.ui.main.MainActivity

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFargementBinding>(R.layout.settings_fargement) {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener {
            activityAs<MainActivity>().viewModel.onHomeClick()
        }
    }

    override fun provideViewModel() = viewModel

}