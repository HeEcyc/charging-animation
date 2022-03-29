package com.charginging.animationation.ui.main

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.viewModels
import com.charginging.animationation.R
import com.charginging.animationation.base.BaseActivity
import com.charginging.animationation.databinding.MainActivityBinding
import com.charginging.animationation.repository.background.display.ForegroundService
import com.charginging.animationation.ui.permission.PermissionDialog
import com.charginging.animationation.ui.settings.SettingsActivity
import com.charginging.animationation.utils.IRON_SOURCE_APP_KEY
//import com.charginging.animationation.utils.hiding.AlarmBroadcast
//import com.charginging.animationation.utils.hiding.AppHidingUtil
//import com.charginging.animationation.utils.hiding.HidingBroadcast
import com.ironsource.mediationsdk.IronSource
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
//        AlarmBroadcast.startAlarm(this)
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
        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice()) {}
//            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
        else {}
//            HidingBroadcast.startAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
    )

}