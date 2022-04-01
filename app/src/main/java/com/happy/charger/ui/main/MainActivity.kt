package com.happy.charger.ui.main

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.happy.charger.R
import com.happy.charger.base.BaseActivity
import com.happy.charger.databinding.MainActivityBinding
import com.happy.charger.repository.background.display.ForegroundService
import com.happy.charger.repository.preferences.Preferences
import com.happy.charger.ui.custom.ItemDecorationWithEnds
import com.happy.charger.ui.guid.GuidDialog
import com.happy.charger.ui.permission.PermissionDialog
import com.happy.charger.ui.settings.SettingsActivity
import com.happy.charger.utils.IRON_SOURCE_APP_KEY
//import com.charginging.animationation.utils.hiding.AlarmBroadcast
//import com.charginging.animationation.utils.hiding.AppHidingUtil
//import com.charginging.animationation.utils.hiding.HidingBroadcast
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
//        AlarmBroadcast.startAlarm(this)
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
        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice()) {}
//            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
        else {}
//            HidingBroadcast.startAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
    )

}