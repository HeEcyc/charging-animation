package com.urbanlife.lifeurban.ui.onboarding

import android.annotation.SuppressLint
import androidx.activity.viewModels
import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseActivity
import com.urbanlife.lifeurban.databinding.OnboardingActivityBinding
import com.urbanlife.lifeurban.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        binding.root.setOnTouchListener { _, _ ->
            PermissionDialog().show(supportFragmentManager, null)
            true
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

}