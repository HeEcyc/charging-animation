package com.ioscharginging.iosanimationation.ui.main

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.viewModels
import com.ioscharginging.iosanimationation.R
import com.ioscharginging.iosanimationation.base.BaseActivity
import com.ioscharginging.iosanimationation.databinding.MainActivityBinding
import com.ioscharginging.iosanimationation.repository.background.display.ForegroundService
import com.ioscharginging.iosanimationation.ui.permission.PermissionDialog
import com.ioscharginging.iosanimationation.ui.settings.SettingsActivity
import com.ioscharginging.iosanimationation.utils.IRON_SOURCE_APP_KEY
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