package com.charful.cheerge.ui.settings

import androidx.activity.viewModels
import com.charful.cheerge.R
import com.charful.cheerge.base.BaseActivity
import com.charful.cheerge.databinding.SettingsFargementBinding
import com.charful.cheerge.utils.setOnClickListener

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsFargementBinding>() {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(::onBackPressed)
    }

    override fun provideViewModel() = viewModel

    override fun provideLayoutId(): Int = R.layout.settings_fargement

}