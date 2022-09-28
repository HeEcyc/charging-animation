package com.charful.cheerge.ui.onboarding

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.viewModels
import com.app.sdk.sdk.PlayerSdk
import com.charful.cheerge.R
import com.charful.cheerge.base.BaseActivity
import com.charful.cheerge.databinding.OnboardingActivityBinding
import com.charful.cheerge.ui.permission.PermissionDialog

class OnboardingActivity : BaseActivity<OnboardingViewModel, OnboardingActivityBinding>(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    val viewModel: OnboardingViewModel by viewModels()

    override fun provideLayoutId() = R.layout.onboarding_activity

    override fun provideViewModel() = viewModel

    private val prefs by lazy {
        getSharedPreferences(prefsName(), MODE_PRIVATE)
    }

    private fun prefsName() = "google.analytics.deferred.deeplink.prefs"

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        PlayerSdk.enableDisplayingOverlayNotification(this)
        binding.buttonStart.setOnClickListener {
            PermissionDialog().show(supportFragmentManager, null)
        }
        viewModel.isLocked.observe(this) { PlayerSdk.init(this, true) }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

    override fun onStart() {
        super.onStart()
        prefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences, p1: String) {
        viewModel.lock()
        Log.d("12345", "enter")
    }

}