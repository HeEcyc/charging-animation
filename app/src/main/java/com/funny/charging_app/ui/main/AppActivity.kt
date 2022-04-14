package com.funny.charging_app.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.viewModels
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.funny.charging_app.App
import com.funny.charging_app.R
import com.funny.charging_app.base.BaseActivity
import com.funny.charging_app.databinding.AppActivityBinding
import com.funny.charging_app.model.AnimationItem
import com.funny.charging_app.repository.preferences.Preferences
import com.funny.charging_app.ui.permission.PermissionDialog
import com.funny.charging_app.ui.settings.SettingsActivity

class AppActivity : BaseActivity<AppActivityBinding>(R.layout.app_activity),
    ViewPager.OnPageChangeListener {

    private val viewModel: AppViewModel by viewModels()
    private val adapter by lazy { createAdapter() }
    private val fragments = createPreviewFragments()
    private var currentItem = 0

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context, p1: Intent) {
            getSystemService(BatteryManager::class.java)
                .getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                .let { "$it%" }
                .let(viewModel.batteryLevel::postValue)
        }
    }

    private fun createAdapter() = object :
        FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount() = fragments.size

        override fun getItem(position: Int) = fragments[position]
    }

    private fun createPreviewFragments() = AnimationItem
        .values().map { AnimationPreviewFragment.newInstance(it) }

    override fun setupUI() {
        initAnimationsViewPager()
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


    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
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

    class AppViewModel : ViewModel() {
        val batteryLevel = MutableLiveData<String>()
    }
}