package com.smooth.battery.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.app.sdk.sdk.DotViewSdk
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import com.smooth.battery.App
import com.smooth.battery.R
import com.smooth.battery.base.BaseActivity
import com.smooth.battery.databinding.MainActivityBinding
import com.smooth.battery.repository.background.display.ForegroundService
import com.smooth.battery.ui.animations.AnimationFragment
import com.smooth.battery.ui.home.HomeFragment
import com.smooth.battery.ui.onboarding.OnboardingActivity
import com.smooth.battery.ui.settings.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModels()

    private var onImagePickerResult: ((ArrayList<Image>) -> Unit)? = null
    private val imagePickerActivityLauncher = registerImagePicker {
        if (it.isNotEmpty()) onImagePickerResult?.invoke(it)
        onImagePickerResult = null
    }

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
            val isGranted = checkPermission(permission) || suspendCoroutine { continuation ->
                askRuntimePermission(permission) { continuation.resumeWith(Result.success(it)) }
            }
            results.add(isGranted)
        }
        onResult(results.all { isGranted -> isGranted })
    }

    private fun checkPermission(permission: String) =
        App.instance.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    fun askStoragePermissions(
        onResult: (Boolean) -> Unit
    ) = askMultipleRuntimePermissions(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        else
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
        lifecycleScope,
        onResult
    )

    fun pickImage(onImage: (Image) -> Unit) {
        onImagePickerResult = { onImage(it.first()) }
        imagePickerActivityLauncher.launch(
            ImagePickerConfig(
                isFolderMode = false,
                rootDirectory = RootDirectory.DCIM,
                isMultipleMode = false,
                isShowNumberIndicator = false,
                maxSize = 1,
            )
        )
    }

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        DotViewSdk.check(this)
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

    override fun onResume() {
        super.onResume()
        if (ForegroundService.instance === null && Settings.canDrawOverlays(this))
            startService(Intent(this, ForegroundService::class.java))
    }

}