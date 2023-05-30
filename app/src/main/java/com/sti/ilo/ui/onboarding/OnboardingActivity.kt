package com.sti.ilo.ui.onboarding

import android.annotation.SuppressLint
import androidx.activity.viewModels
import com.sti.ilo.R
import com.sti.ilo.base.BaseActivity
import com.sti.ilo.databinding.OnboardingActivityBinding
import com.sti.ilo.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        PermissionDialog().show(supportFragmentManager, null)
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

}