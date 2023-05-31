package com.bio.oiq.ui.settings

import androidx.activity.viewModels
import com.bio.oiq.R
import com.bio.oiq.base.BaseActivity
import com.bio.oiq.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

    override fun provideViewModel() = viewModel

}