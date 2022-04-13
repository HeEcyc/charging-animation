package com.charginging.animationation.ui.main

import android.content.Intent
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.charginging.animationation.R
import com.charginging.animationation.base.BaseActivity
import com.charginging.animationation.databinding.AppActivityBinding
import com.charginging.animationation.model.AnimationItem
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
//        if (!Settings.canDrawOverlays(this))
//            PermissionDialog().show(supportFragmentManager, null)

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun initAnimationsViewPager() {
        binding.animationsList.adapter = adapter
        binding.animationsList.setOnPageChangeListener(this)
    }

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