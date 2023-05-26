package com.sti.ilo.ui.settings

import androidx.activity.viewModels
import com.sti.ilo.R
import com.sti.ilo.base.BaseActivity
import com.sti.ilo.databinding.SettingsFargementBinding
import com.sti.ilo.utils.setOnClickListener

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsFargementBinding>() {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(::onBackPressed)
    }

    override fun provideViewModel() = viewModel

    override fun provideLayoutId(): Int = R.layout.settings_fargement

}