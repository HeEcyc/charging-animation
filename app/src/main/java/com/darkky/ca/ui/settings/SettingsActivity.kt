package com.darkky.ca.ui.settings

import androidx.activity.viewModels
import com.darkky.ca.R
import com.darkky.ca.base.BaseActivity
import com.darkky.ca.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

    override fun provideViewModel() = viewModel

}