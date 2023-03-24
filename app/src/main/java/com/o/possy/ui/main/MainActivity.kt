package com.o.possy.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.viewModels
import com.o.possy.R
import com.o.possy.base.BaseActivity
import com.o.possy.databinding.MainActivityBinding
import com.o.possy.repository.background.display.ForegroundService
import com.o.possy.repository.preferences.Preferences
import com.o.possy.ui.catalog.CatalogActivity
import com.o.possy.ui.greeting.GreetingActivity
import com.o.possy.ui.permission.PermissionDialog
import com.o.possy.ui.settings.SettingsActivity

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