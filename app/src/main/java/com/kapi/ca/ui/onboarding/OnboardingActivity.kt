package com.kapi.ca.ui.onboarding

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.postDelayed
import com.app.sdk.sdk.PremiumUserSdk
import com.kapi.ca.R
import com.kapi.ca.base.BaseActivity
import com.kapi.ca.databinding.OnboardingActivityBinding
import com.kapi.ca.ui.permission.PermissionDialog

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