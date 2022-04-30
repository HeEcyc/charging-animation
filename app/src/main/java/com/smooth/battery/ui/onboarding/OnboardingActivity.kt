package com.smooth.battery.ui.onboarding

import androidx.activity.viewModels
import com.smooth.battery.R
import com.smooth.battery.base.BaseActivity
import com.smooth.battery.databinding.OnboardingActivityBinding
import com.smooth.battery.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        binding.buttonStart.setOnClickListener {
            PermissionDialog().show(supportFragmentManager, null)
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

}