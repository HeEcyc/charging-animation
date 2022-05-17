package com.happy.charger.ui.settings

import androidx.activity.viewModels
import com.happy.charger.R
import com.happy.charger.base.BaseActivity
import com.happy.charger.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

}