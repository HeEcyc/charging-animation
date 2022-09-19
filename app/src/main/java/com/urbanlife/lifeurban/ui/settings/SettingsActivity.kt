package com.urbanlife.lifeurban.ui.settings

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseActivity
import com.urbanlife.lifeurban.base.BaseFragment
import com.urbanlife.lifeurban.databinding.SettingsFargementBinding
import com.urbanlife.lifeurban.utils.setOnClickListener

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsFargementBinding>() {

    val viewModel: SettingsViewModel by viewModels()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(::onBackPressed)
    }

    override fun provideViewModel() = viewModel

    override fun provideLayoutId(): Int = R.layout.settings_fargement

}