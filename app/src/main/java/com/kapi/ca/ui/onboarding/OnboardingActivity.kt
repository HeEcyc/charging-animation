package com.kapi.ca.ui.onboarding

import androidx.activity.viewModels
import androidx.core.view.postDelayed
import com.kapi.ca.R
import com.kapi.ca.base.BaseActivity
import com.kapi.ca.databinding.OnboardingActivityBinding
import com.kapi.ca.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        binding.root.postDelayed(2000) {
            PermissionDialog().show(supportFragmentManager, null)
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

}