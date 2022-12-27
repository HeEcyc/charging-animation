package com.dec.ces.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.ScrollView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dec.ces.App
import com.dec.ces.R
import com.dec.ces.base.BaseActivity
import com.dec.ces.databinding.MainActivityBinding
import com.dec.ces.model.animation.Animation
import com.dec.ces.repository.background.display.ForegroundService
import com.dec.ces.repository.preferences.Preferences
import com.dec.ces.ui.greeting.GreetingActivity
import com.dec.ces.ui.permission.PermissionDialog
import com.dec.ces.ui.preview.PreviewActivity
import com.dec.ces.ui.settings.SettingsActivity
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine
import kotlin.math.absoluteValue
import kotlin.math.min

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Preferences.hasShownGreeting)
            startActivity(Intent(this, GreetingActivity::class.java))
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)

        binding.vp2.apply {
            children.firstOrNull { it is RecyclerView }?.let { it as RecyclerView }?.overScrollMode = ScrollView.OVER_SCROLL_NEVER
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer { page, position ->
                // [0.767;1] 1 - selected; 0.7772 - not selected
                val scaleValue = 0.7772f + 0.2228f * (1 - min(1f, position.absoluteValue))
                page.scaleX = scaleValue
                page.scaleY = scaleValue
                // 3.4028235E38 - max translation value possible
                page.translationZ = if (position == 0f) 3.4028235E38f else min(3.4028235E38f, 1 / position.absoluteValue)
                page.translationX = -position * binding.vp2.height * 0.4f
                page.findViewById<View>(R.id.icAdd).translationX = -position * binding.vp2.height * 0.2278f
            }
            adapter = viewModel.adapter
            currentItem = 1
        }
        binding.buttonApply.setOnClickListener {
            val animation = viewModel.adapter.getData()[binding.vp2.currentItem]
            if (animation is Animation.Companion.NewAnimation)
                askStoragePermissions { res ->
                    if (res) pickImage {
                        viewModel.addCustomAnimation(it.uri, this)
                    }
                }
            else
                startActivity(PreviewActivity.getIntent(this, animation))
        }
        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

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

    private fun askStoragePermissions(
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

    private fun pickImage(onImage: (Image) -> Unit) {
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

}