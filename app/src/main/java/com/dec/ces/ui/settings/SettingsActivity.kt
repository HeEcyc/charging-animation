package com.dec.ces.ui.settings

import androidx.activity.viewModels
import com.dec.ces.R
import com.dec.ces.base.BaseActivity
import com.dec.ces.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

    override fun provideViewModel() = viewModel

}