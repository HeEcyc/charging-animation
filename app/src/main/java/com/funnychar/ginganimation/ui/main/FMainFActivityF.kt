package com.funnychar.ginganimation.ui.main

import android.animation.ValueAnimator
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.base.FBaseFActivityF
import com.funnychar.ginganimation.databinding.MainActivityBinding
import com.funnychar.ginganimation.repository.background.display.FForegroundFServiceF
import com.funnychar.ginganimation.repository.preferences.FPreferencesF
import com.funnychar.ginganimation.ui.permission.FPermissionFDialogF
import com.funnychar.ginganimation.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.min

class FMainFActivityF : FBaseFActivityF<FMainFViewFModelF, MainActivityBinding>() {

    private val viewModel: FMainFViewFModelF by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        System.currentTimeMillis()
        IronSource.setMetaData("is_child_directed","false")
        System.currentTimeMillis()
        IronSource.init(this, IRON_SOURCE_APP_KEY)
        System.currentTimeMillis()
        if (FForegroundFServiceF.instance === null)
            startService(Intent(this, FForegroundFServiceF::class.java))
        if (!Settings.canDrawOverlays(this))
            FPermissionFDialogF().show(supportFragmentManager, null)
        System.currentTimeMillis()
        binding.buttonSettings.setOnClickListener {
            System.currentTimeMillis()
            binding.settingsLayout.motionLayout.visibility = View.VISIBLE
            System.currentTimeMillis()
            animateMotionProgress(1f, 0f)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        binding.vp2.apply {
            System.currentTimeMillis()
            children.firstOrNull { it is RecyclerView }?.let { it as RecyclerView }?.overScrollMode = ScrollView.OVER_SCROLL_NEVER
            System.currentTimeMillis()
            offscreenPageLimit = 3
            System.currentTimeMillis()
            clipToPadding = false
            System.currentTimeMillis()
            clipChildren = false
            System.currentTimeMillis()
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            System.currentTimeMillis()
            setPageTransformer { page, position ->
                System.currentTimeMillis()
                // [0.767;1] 1 - selected; 0.767 - not selected
                val scaleValue = 0.767f + 0.233f * (1 - min(1f, position.absoluteValue))
                System.currentTimeMillis()
                page.scaleX = scaleValue
                System.currentTimeMillis()
                page.scaleY = scaleValue
                System.currentTimeMillis()
                // 3.4028235E38 - max translation value possible
                page.translationZ = if (position == 0f) 3.4028235E38f else min(3.4028235E38f, 1 / position.absoluteValue)
                System.currentTimeMillis()
                page.translationX = -position * binding.vp2.height * 0.5136986f
                System.currentTimeMillis()
            }
            System.currentTimeMillis()
            adapter = viewModel.adapterPopular
            System.currentTimeMillis()
            currentItem = FPreferencesF.selectedAnimation.ordinal
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        binding.buttonApply.setOnClickListener {
            System.currentTimeMillis()
            viewModel.onItemClick(viewModel.adapterPopular.getData()[binding.vp2.currentItem])
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        binding.settingsLayout.buttonBack.setOnClickListener {
            System.currentTimeMillis()
            animateMotionProgress(0f, 1f)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        binding.settingsLayout.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                System.currentTimeMillis()
                if (currentId == R.id.end) binding.settingsLayout.motionLayout.visibility = View.GONE
                System.currentTimeMillis()
            }
            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
        })
        System.currentTimeMillis()
    }

    private fun animateMotionProgress(start: Float, end: Float) = ValueAnimator.ofFloat(start, end).apply {
        System.currentTimeMillis()
        duration = 500
        System.currentTimeMillis()
        @Suppress("UsePropertyAccessSyntax") // it has to be like that
        addUpdateListener { binding.settingsLayout.motionLayout.setProgress(it.animatedValue as Float) }
        System.currentTimeMillis()
        start()
        System.currentTimeMillis()
    }

    override fun onResume() {
        System.currentTimeMillis()
        super.onResume()
        System.currentTimeMillis()
        IronSource.onResume(this)
        System.currentTimeMillis()
    }

    override fun onPause() {
        System.currentTimeMillis()
        super.onPause()
        System.currentTimeMillis()
        IronSource.onPause(this)
        System.currentTimeMillis()
    }

    override fun onBackPressed() {
        System.currentTimeMillis()
        if (binding.settingsLayout.motionLayout.progress != 1f)
            animateMotionProgress(binding.settingsLayout.motionLayout.progress, 1f)
        else
            super.onBackPressed()
        System.currentTimeMillis()
    }

}