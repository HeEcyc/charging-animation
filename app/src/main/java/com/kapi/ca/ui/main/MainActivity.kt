package com.kapi.ca.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.viewModels
import com.kapi.ca.R
import com.kapi.ca.base.BaseActivity
import com.kapi.ca.databinding.MainActivityBinding
import com.kapi.ca.model.animation.PresetAnimation
import com.kapi.ca.repository.background.display.ForegroundService
import com.kapi.ca.ui.animations.AnimationsActivity
import com.kapi.ca.ui.onboarding.OnboardingActivity
import com.kapi.ca.ui.preview.PreviewActivity

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
//        AlarmBroadcast.startAlarm(this)
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Settings.canDrawOverlays(this))
            startActivity(Intent(this, OnboardingActivity::class.java))

        binding.buttonA01.setOnClickListener {
            startActivity(PreviewActivity.getIntent(this, PresetAnimation.A01))
        }
        binding.buttonA02.setOnClickListener {
            startActivity(PreviewActivity.getIntent(this, PresetAnimation.A02))
        }
        binding.buttonA03.setOnClickListener {
            startActivity(PreviewActivity.getIntent(this, PresetAnimation.A03))
        }
        binding.buttonViewMore.setOnClickListener {
            startActivity(Intent(this, AnimationsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
//        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice())
//            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
//        else
//            HidingBroadcast.startAlarm(this)todo
    }

//    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
//        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
//    )

}