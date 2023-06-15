package com.kapi.ca.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.viewModels
import com.app.sdk.sdk.PremiumUserSdk
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
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))

        if (!Settings.canDrawOverlays(this))
            startActivity(Intent(this, OnboardingActivity::class.java))

        binding.buttonA01.setOnClickListener { displayAnimation(PresetAnimation.A01) }
        binding.buttonA02.setOnClickListener { displayAnimation(PresetAnimation.A02) }
        binding.buttonA03.setOnClickListener { displayAnimation(PresetAnimation.A03) }
        binding.buttonViewMore.setOnClickListener {
            startActivity(Intent(this, AnimationsActivity::class.java))
        }
    }

    private fun displayAnimation(presetAnimation: PresetAnimation) {
        PremiumUserSdk.showInAppAd(this) {
            startActivity(PreviewActivity.getIntent(this, presetAnimation))
        }
    }
}