package com.juno.ca.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.viewModels
import com.juno.ca.R
import com.juno.ca.base.BaseActivity
import com.juno.ca.databinding.MainActivityBinding
import com.juno.ca.repository.background.display.ForegroundService
import com.juno.ca.repository.preferences.Preferences
import com.juno.ca.ui.catalog.CatalogActivity
import com.juno.ca.ui.greeting.GreetingActivity
import com.juno.ca.ui.permission.PermissionDialog
import com.juno.ca.ui.settings.SettingsActivity

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Preferences.hasShownGreeting)
            startActivity(Intent(this, GreetingActivity::class.java))
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.buttonAnimations.setOnClickListener {
            startActivity(Intent(this, CatalogActivity::class.java))
        }
    }

}