package com.o.possy.ui.main

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.o.possy.R
import com.o.possy.base.BaseActivity
import com.o.possy.databinding.MainActivityBinding
import com.o.possy.repository.background.display.ForegroundService
import com.o.possy.repository.preferences.Preferences
import com.o.possy.ui.custom.ItemDecorationWithEnds
import com.o.possy.ui.greeting.GreetingActivity
import com.o.possy.ui.permission.PermissionDialog
import com.o.possy.ui.preview.PreviewActivity
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

        binding.rv.post {
            val baseLengthUnit = binding.rv.width / 360
            var itemDecoration = ItemDecorationWithEnds(
                topFirst = baseLengthUnit * 42,
                top = 0,
                topLast = 0,
                bottomFirst = baseLengthUnit * 20,
                bottom = baseLengthUnit * 20,
                bottomLast = baseLengthUnit * 130,
                firstPredicate = { i -> i in 0..1 },
                lastPredicate = { i, c -> if (c % 2 == 0) i in (c - 2) until c else i == c - 1 }
            )
            binding.rv.addItemDecoration(itemDecoration)
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = baseLengthUnit * 16
            val innerSpace = baseLengthUnit * 8
            itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLTR) outerSpace else innerSpace,
                leftLast = if (isLTR) innerSpace else outerSpace,
                rightFirst = if (isLTR) innerSpace else outerSpace,
                rightLast = if (isLTR) outerSpace else innerSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            binding.rv.addItemDecoration(itemDecoration)
        }
        viewModel.showPreview.observe(this) {
            startActivity(PreviewActivity.getIntent(this, it))
        }
        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

}