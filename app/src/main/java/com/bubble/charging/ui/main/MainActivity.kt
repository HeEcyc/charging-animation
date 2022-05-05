package com.bubble.charging.ui.main

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.viewModels
import com.bubble.charging.R
import com.bubble.charging.base.BaseActivity
import com.bubble.charging.databinding.MainActivityBinding
import com.bubble.charging.repository.background.display.ForegroundService
import com.bubble.charging.ui.permission.PermissionDialog
import com.bubble.charging.ui.settings.SettingsActivity
import com.bubble.charging.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
//        AlarmBroadcast.startAlarm(this) todo
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
//        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice())
//            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
//        else
//            HidingBroadcast.startAlarm(this)todo
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
    )

}