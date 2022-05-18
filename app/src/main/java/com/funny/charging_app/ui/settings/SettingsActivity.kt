package com.funny.charging_app.ui.settings

import androidx.activity.viewModels
import com.funny.charging_app.R
import com.funny.charging_app.base.BaseActivity
import com.funny.charging_app.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

}