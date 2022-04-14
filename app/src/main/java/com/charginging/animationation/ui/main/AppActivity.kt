package com.charginging.animationation.ui.main

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.charginging.animationation.App
import com.charginging.animationation.R
import com.charginging.animationation.base.BaseActivity
import com.charginging.animationation.databinding.AppActivityBinding
import com.charginging.animationation.model.AnimationItem
import com.charginging.animationation.repository.preferences.Preferences
import com.charginging.animationation.ui.permission.PermissionDialog
import com.charginging.animationation.ui.settings.SettingsActivity

class AppActivity : BaseActivity<AppActivityBinding>(R.layout.app_activity),
    ViewPager.OnPageChangeListener {
    private val adapter by lazy { createAdapter() }
    private val fragments = createPreviewFragments()
    private var currentItem = 0

    private fun createAdapter() = object :
        FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount() = fragments.size

        override fun getItem(position: Int) = fragments[position]
    }

    private fun createPreviewFragments() = AnimationItem
        .values().map { AnimationPreviewFragment.newInstance(it) }

    override fun setupUI() {
        initAnimationsViewPager()
//
//        if (ForegroundService.instance === null)
//            startService(Intent(this, ForegroundService::class.java))
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)
        else App.instance.startForegroundService()
        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.applyButton.setOnClickListener {
            Preferences.selectedAnimation = AnimationItem.values()[binding.animationsList.realItem]
        }
    }

    private fun initAnimationsViewPager() {
        binding.animationsList.adapter = adapter
        currentItem = getSelectedAnimationPosition()
        binding.animationsList.setOnPageChangeListener(this)
        binding.animationsList.currentItem = currentItem
        Handler(Looper.getMainLooper())
            .postDelayed({ fragments[currentItem].startAnimation() }, 1000)
    }


    private fun getSelectedAnimationPosition() = AnimationItem
        .values().indexOfFirst { it == Preferences.selectedAnimation }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        fragments[currentItem].stopAnimation()
        fragments[binding.animationsList.realItem].startAnimation()
        currentItem = binding.animationsList.realItem
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

}