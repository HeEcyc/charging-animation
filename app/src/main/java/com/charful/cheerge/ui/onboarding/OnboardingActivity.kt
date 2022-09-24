package com.charful.cheerge.ui.onboarding

import android.annotation.SuppressLint
import androidx.activity.viewModels
import com.charful.cheerge.R
import com.charful.cheerge.base.BaseActivity
import com.charful.cheerge.databinding.OnboardingActivityBinding
import com.charful.cheerge.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        binding.buttonStart.setOnClickListener {
            PermissionDialog().show(supportFragmentManager, null)
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

}