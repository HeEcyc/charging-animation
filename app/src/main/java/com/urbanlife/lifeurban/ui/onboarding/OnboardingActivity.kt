package com.urbanlife.lifeurban.ui.onboarding

import androidx.activity.viewModels
import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseActivity
import com.urbanlife.lifeurban.databinding.OnboardingActivityBinding
import com.urbanlife.lifeurban.ui.permission.PermissionDialog

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