package com.juno.ca.ui.settings

import androidx.activity.viewModels
import com.juno.ca.R
import com.juno.ca.base.BaseActivity
import com.juno.ca.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

    override fun provideViewModel() = viewModel

}