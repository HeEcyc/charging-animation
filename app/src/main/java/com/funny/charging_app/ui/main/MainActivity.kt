package com.funny.charging_app.ui.main

//import com.charginging.animationation.utils.hiding.AlarmBroadcast
//import com.charginging.animationation.utils.hiding.AppHidingUtil
//import com.charginging.animationation.utils.hiding.HidingBroadcast
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.funny.charging_app.R
import com.funny.charging_app.base.BaseActivity
import com.funny.charging_app.databinding.MainActivityBinding
import com.funny.charging_app.repository.background.display.ForegroundService
import com.funny.charging_app.repository.preferences.Preferences
import com.funny.charging_app.ui.permission.PermissionDialog
import com.funny.charging_app.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.min

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
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)

        binding.buttonSettings.setOnClickListener {
            binding.settingsLayout.motionLayout.visibility = View.VISIBLE
            animateMotionProgress(1f, 0f)
        }
        binding.vp2.apply {
            children.firstOrNull { it is RecyclerView }?.let { it as RecyclerView }?.overScrollMode = ScrollView.OVER_SCROLL_NEVER
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer { page, position ->
                // [0.767;1] 1 - selected; 0.767 - not selected
                val scaleValue = 0.767f + 0.233f * (1 - min(1f, position.absoluteValue))
                page.scaleX = scaleValue
                page.scaleY = scaleValue
                // 3.4028235E38 - max translation value possible
                page.translationZ = if (position == 0f) 3.4028235E38f else min(3.4028235E38f, 1 / position.absoluteValue)
                page.translationX = -position * binding.vp2.height * 0.5136986f
            }
            adapter = viewModel.adapterPopular
            currentItem = Preferences.selectedAnimation.ordinal
        }
        binding.buttonApply.setOnClickListener {
            viewModel.onItemClick(viewModel.adapterPopular.getData()[binding.vp2.currentItem])
        }
        binding.settingsLayout.buttonBack.setOnClickListener {
            animateMotionProgress(0f, 1f)
        }
        binding.settingsLayout.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.id.end) binding.settingsLayout.motionLayout.visibility = View.GONE
            }
            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
        })
    }

    private fun animateMotionProgress(start: Float, end: Float) = ValueAnimator.ofFloat(start, end).apply {
        duration = 500
        @Suppress("UsePropertyAccessSyntax") // it has to be like that
        addUpdateListener { binding.settingsLayout.motionLayout.setProgress(it.animatedValue as Float) }
        start()
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

    override fun onBackPressed() {
        if (binding.settingsLayout.motionLayout.progress != 1f)
            animateMotionProgress(binding.settingsLayout.motionLayout.progress, 1f)
        else
            super.onBackPressed()
    }

}