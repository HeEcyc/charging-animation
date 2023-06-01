package com.bio.oiq.ui.main

import android.content.Intent
import android.provider.Settings
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import com.bio.oiq.R
import com.bio.oiq.base.BaseActivity
import com.bio.oiq.databinding.MainActivityBinding
import com.bio.oiq.repository.background.display.ForegroundService
import com.bio.oiq.repository.preferences.Preferences
import com.bio.oiq.ui.custom.ItemDecorationWithEnds
import com.bio.oiq.ui.greeting.GreetingActivity
import com.bio.oiq.ui.permission.PermissionDialog
import com.bio.oiq.ui.preview.PreviewActivity

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun provideLayoutId() = R.layout.main_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        if (ForegroundService.instance === null)
            startService(Intent(this, ForegroundService::class.java))
        if (!Preferences.hasShownGreeting)
            startActivity(Intent(this, GreetingActivity::class.java))
        if (!Settings.canDrawOverlays(this))
            PermissionDialog().show(supportFragmentManager, null)

        binding.rv.post {
            val baseLengthUnit = binding.rv.width / 360
            var itemDecoration = ItemDecorationWithEnds(
                bottom = baseLengthUnit * 16
            )
            binding.rv.addItemDecoration(itemDecoration)
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = baseLengthUnit * 16
            val innerSpace = baseLengthUnit * 8
            itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLTR) outerSpace else innerSpace,
                leftLast = if (isLTR) innerSpace else outerSpace,
                rightFirst = if (isLTR) innerSpace else outerSpace,
                rightLast = if (isLTR) outerSpace else innerSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            binding.rv.addItemDecoration(itemDecoration)
        }
        viewModel.showPreview.observe(this) {
            PreviewActivity().apply {
                animation = it
                show(supportFragmentManager, null)
            }
        }
        binding.buttonSettings.setOnClickListener {
            openDrawer()
        }
        binding.buttonMenuBack.setOnClickListener {
            closeDrawer()
        }
    }

    override fun onResume() {
        viewModel.isAppOn.set(Preferences.showWhenUnlocked)
        super.onResume()
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