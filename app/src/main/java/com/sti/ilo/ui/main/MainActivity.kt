package com.sti.ilo.ui.main

import android.content.Intent
import android.provider.Settings
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import com.sti.ilo.R
import com.sti.ilo.base.BaseActivity
import com.sti.ilo.databinding.MainActivityBinding
import com.sti.ilo.repository.background.display.ForegroundService
import com.sti.ilo.repository.preferences.Preferences
import com.sti.ilo.ui.onboarding.OnboardingActivity

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        if (Preferences.firstLaunchMillis == -1L)
            Preferences.firstLaunchMillis = System.currentTimeMillis()

//        AlarmBroadcast.startAlarm(this)

        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))

        binding.buttonSettings.setOnClickListener {
            openDrawer()
        }
        binding.buttonMenuBack.setOnClickListener {
            closeDrawer()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice())
//            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
//        else
//            HidingBroadcast.startAlarm(this)
//    }

//    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
//        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
//    )

    override fun onResume() {
        viewModel.isAppOn.set(Preferences.showWhenUnlocked)
        super.onResume()
        if (!Settings.canDrawOverlays(this))
            startActivity(Intent(this, OnboardingActivity::class.java))
    }

    private fun openDrawer() {
        binding.drawerContainer.visibility = View.VISIBLE
        binding.drawerContainer.animate().alpha(1f).setDuration(100).withEndAction {
            val cs = ConstraintSet()
            cs.clone(binding.drawerContainer)
            cs.connect(
                R.id.drawer,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            cs.clear(R.id.drawer, ConstraintSet.START)
            TransitionManager.beginDelayedTransition(
                binding.drawerContainer,
                ChangeBounds().setDuration(100)
            )
            cs.applyTo(binding.drawerContainer)
        }.start()
    }

    private fun closeDrawer() {
        val cs = ConstraintSet()
        cs.clone(binding.drawerContainer)
        cs.connect(R.id.drawer, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END)
        cs.clear(R.id.drawer, ConstraintSet.END)
        val transition =
            ChangeBounds().setDuration(100).addListener(object : Transition.TransitionListener {
                override fun onTransitionStart(transition: Transition) {}
                override fun onTransitionEnd(transition: Transition) {
                    binding.drawerContainer.animate().alpha(0f).setDuration(100).withEndAction {
                        binding.drawerContainer.visibility = View.GONE
                    }
                }

                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionResume(transition: Transition) {}
            })
        TransitionManager.beginDelayedTransition(binding.drawerContainer, transition)
        cs.applyTo(binding.drawerContainer)
    }

    override fun onBackPressed() {
        if (binding.drawerContainer.visibility == View.VISIBLE)
            closeDrawer()
        else
            finish()
    }

}