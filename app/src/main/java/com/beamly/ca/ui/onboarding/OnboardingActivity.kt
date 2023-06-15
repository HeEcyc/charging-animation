package com.beamly.ca.ui.onboarding

import android.content.Intent
import androidx.activity.viewModels
import com.app.sdk.sdk.PremiumUserSdk
import com.beamly.ca.R
import com.beamly.ca.base.BaseActivity
import com.beamly.ca.databinding.OnboardingActivityBinding
import com.beamly.ca.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        PermissionDialog().show(supportFragmentManager, null)
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        PremiumUserSdk.onResult(this)
    }

}