package com.smooth.battery.ui.main

//import com.charginging.animationation.utils.hiding.AlarmBroadcast
//import com.charginging.animationation.utils.hiding.AppHidingUtil
//import com.charginging.animationation.utils.hiding.HidingBroadcast
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.smooth.battery.R
import com.smooth.battery.base.BaseActivity
import com.smooth.battery.databinding.MainActivityBinding
import com.smooth.battery.repository.background.display.ForegroundService
import com.smooth.battery.repository.preferences.Preferences
import com.smooth.battery.ui.guid.GuidDialog
import com.smooth.battery.ui.permission.PermissionDialog
import com.smooth.battery.ui.settings.SettingsActivity
import com.smooth.battery.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

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
        binding.vp2.apply {
            children.firstOrNull { it is RecyclerView }?.let { it as RecyclerView }?.overScrollMode = ScrollView.OVER_SCROLL_NEVER
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            orientation = ViewPager2.ORIENTATION_VERTICAL
            setPageTransformer { page, position ->
                if (position == 0f) {
                    page.scaleX = 0.555f
                    page.scaleY = 0.555f
                    page.translationZ = 1000f
                    page.findViewById<View>(R.id.buttonContainer).visibility = View.VISIBLE
                } else {
                    page.scaleX = 0.333f
                    page.scaleY = 0.333f
                    page.translationZ = 0f
                    page.findViewById<View>(R.id.buttonContainer).visibility = View.GONE
                }
                page.translationY = -position * binding.root.height * 0.666f
            }
            adapter = viewModel.adapterPopular
            currentItem = Preferences.selectedAnimation.ordinal
        }
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