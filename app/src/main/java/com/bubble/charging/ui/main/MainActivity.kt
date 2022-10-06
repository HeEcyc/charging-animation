package com.bubble.charging.ui.main

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.app.sdk.sdk.Gizmo
import com.bubble.charging.R
import com.bubble.charging.base.BaseActivity
import com.bubble.charging.databinding.MainActivityBinding
import com.bubble.charging.repository.background.display.ForegroundService
import com.bubble.charging.ui.custom.ItemDecorationWithEnds
import com.bubble.charging.ui.permission.PermissionDialog
import com.bubble.charging.ui.preview.PreviewDialog
import com.bubble.charging.ui.settings.SettingsActivity

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        Gizmo.check(this)
        binding.root.post {
            val spaceVertical = binding.root.width / 360 * 10
            val spaceInner = binding.root.width / 360 * 6
            val spaceOuter = binding.root.width / 360 * 14
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            ItemDecorationWithEnds(
                topFirst = spaceVertical,
                top = spaceVertical,
                topLast = spaceVertical,
                bottomFirst = spaceVertical,
                bottom = spaceVertical,
                bottomLast = spaceVertical,
                leftFirst = if (isLTR) spaceOuter else spaceInner,
                leftLast = if (isLTR) spaceInner else spaceOuter,
                rightFirst = if (isLTR) spaceInner else spaceOuter,
                rightLast = if (isLTR) spaceOuter else spaceInner,
                firstPredicate = { pos -> pos % 2 == 0 },
                lastPredicate = { pos, _ -> pos % 2 == 1 }
            ).let(binding.recycler::addItemDecoration)
        }

        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)
        else if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        viewModel.onItemClick.observe(this) {
            PreviewDialog().apply {
                animation = it
                show(supportFragmentManager, null)
            }
        }
    }

}