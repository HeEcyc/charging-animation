package com.beamly.ca.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleCoroutineScope
import com.beamly.ca.App
import com.beamly.ca.R
import com.beamly.ca.base.BaseActivity
import com.beamly.ca.databinding.MainActivityBinding
import com.beamly.ca.repository.background.display.ForegroundService
import com.beamly.ca.ui.animations.AnimationFragment
import com.beamly.ca.ui.home.HomeFragment
import com.beamly.ca.ui.onboarding.OnboardingActivity
import com.beamly.ca.ui.settings.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModels()

    private var onRuntimePermissionResult: ((Boolean) -> Unit)? = null
    private val runtimePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onRuntimePermissionResult?.invoke(it)
            onRuntimePermissionResult = null
        }

    private fun askRuntimePermission(permission: String, onResult: (Boolean) -> Unit) =
        if (checkPermission(permission))
            onResult(true)
        else {
            onRuntimePermissionResult = onResult
            runtimePermissionLauncher.launch(permission)
        }

    private fun askMultipleRuntimePermissions(
        permissions: List<String>,
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = lifecycleCoroutineScope.launch(Dispatchers.Main) {
        val results = mutableListOf<Boolean>()
        permissions.forEach { permission ->
            val isGranted = checkPermission(permission) || suspendCoroutine {  continuation ->
                askRuntimePermission(permission) { continuation.resumeWith(Result.success(it)) }
            }
            results.add(isGranted)
        }
        onResult(results.all { isGranted -> isGranted })
    }

    private fun checkPermission(permission: String) =
        App.instance.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Settings.canDrawOverlays(this))
            startActivity(Intent(this, OnboardingActivity::class.java))

        viewModel.showAnimation.observe(this) {
            supportFragmentManager.commit { replace(R.id.fragmentContainer, AnimationFragment()) }
        }
        viewModel.showHome.observe(this) {
            supportFragmentManager.commit { replace(R.id.fragmentContainer, HomeFragment()) }
        }
        viewModel.showSettings.observe(this) {
            supportFragmentManager.commit { replace(R.id.fragmentContainer, SettingsFragment()) }
        }
    }
}