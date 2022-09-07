package com.rocket.charge.ui.onboarding

import androidx.activity.viewModels
import com.rocket.charge.R
import com.rocket.charge.base.BaseActivity
import com.rocket.charge.databinding.OnboardingActivityBinding
import com.rocket.charge.ui.permission.PermissionDialog

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