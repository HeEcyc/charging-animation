package com.o.possy.ui.settings

import androidx.activity.viewModels
import com.o.possy.R
import com.o.possy.base.BaseActivity
import com.o.possy.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun provideLayoutId() = R.layout.settings_activity

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
    }

    override fun provideViewModel() = viewModel

}