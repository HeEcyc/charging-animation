package com.smooth.battery.ui.main

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.ironsource.mediationsdk.IronSource
import com.smooth.battery.R
import com.smooth.battery.base.BaseActivity
import com.smooth.battery.databinding.MainActivityBinding
import com.smooth.battery.repository.background.display.ForegroundService
import com.smooth.battery.ui.home.HomeFragment
import com.smooth.battery.ui.onboarding.OnboardingActivity
import com.smooth.battery.ui.settings.SettingsFragment
import com.smooth.battery.utils.IRON_SOURCE_APP_KEY
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
//        AlarmBroadcast.startAlarm(this)
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Settings.canDrawOverlays(this))
            startActivity(Intent(this, OnboardingActivity::class.java))

        viewModel.showAnimation.observe(this) {
            //todo
        }
        viewModel.showHome.observe(this) {
            supportFragmentManager.commit { replace(R.id.fragmentContainer, HomeFragment()) }
        }
        viewModel.showSettings.observe(this) {
            supportFragmentManager.commit { replace(R.id.fragmentContainer, SettingsFragment()) }
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