package com.happy.charger.ui.main

//import com.charginging.animationation.utils.hiding.AlarmBroadcast
//import com.charginging.animationation.utils.hiding.AppHidingUtil
//import com.charginging.animationation.utils.hiding.HidingBroadcast
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.happy.charger.R
import com.happy.charger.base.BaseActivity
import com.happy.charger.databinding.MainActivityBinding
import com.happy.charger.repository.background.display.ForegroundService
import com.happy.charger.repository.preferences.Preferences
import com.happy.charger.ui.guid.GuidDialog
import com.happy.charger.ui.permission.PermissionDialog
import com.happy.charger.ui.settings.SettingsActivity
import com.happy.charger.utils.IRON_SOURCE_APP_KEY
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