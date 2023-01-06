package com.dec.ces.ui.main

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.dec.ces.R
import com.dec.ces.base.BaseActivity
import com.dec.ces.databinding.MainActivityBinding
import com.dec.ces.repository.background.display.ForegroundService
import com.dec.ces.repository.preferences.Preferences
import com.dec.ces.ui.custom.ItemDecorationWithEnds
import com.dec.ces.ui.greeting.GreetingActivity
import com.dec.ces.ui.permission.PermissionDialog
import com.dec.ces.ui.preview.PreviewActivity
import com.dec.ces.ui.settings.SettingsActivity

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