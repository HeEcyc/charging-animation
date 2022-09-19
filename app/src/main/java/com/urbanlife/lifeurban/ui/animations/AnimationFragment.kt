package com.urbanlife.lifeurban.ui.animations

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ShareCompat
import androidx.fragment.app.viewModels
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseFragment
import com.urbanlife.lifeurban.databinding.AnimationFragmentBinding
import com.urbanlife.lifeurban.repository.preferences.Preferences
import com.urbanlife.lifeurban.ui.custom.ItemDecorationWithEnds
import com.urbanlife.lifeurban.ui.main.MainActivity
import com.urbanlife.lifeurban.ui.preview.PreviewActivity
import com.urbanlife.lifeurban.ui.settings.SettingsActivity
import com.urbanlife.lifeurban.utils.APP_LINK
import com.urbanlife.lifeurban.utils.setOnClickListener

class AnimationFragment : BaseFragment<AnimationViewModel, AnimationFragmentBinding>(R.layout.animation_fragment) {

    val viewModel: AnimationViewModel by viewModels()

    override fun setupUI() {
        binding.root.post {
            val spaceInner = binding.root.width / 360 * 8
            val spaceOuter = binding.root.width / 360 * 20
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            var decoration = ItemDecorationWithEnds(
                leftFirst = if (isLTR) spaceOuter else spaceInner,
                leftLast = if (isLTR) spaceInner else spaceOuter,
                rightFirst = if (isLTR) spaceInner else spaceOuter,
                rightLast = if (isLTR) spaceOuter else spaceInner,
                firstPredicate = { pos -> pos % 2 == 0 },
                lastPredicate = { pos, _ -> pos % 2 == 1 }
            )
            binding.recyclerAnimation.addItemDecoration(decoration)
            binding.recyclerImage.addItemDecoration(decoration)
            val spaceVertical = binding.root.width / 360 * 16
            val spaceVerticaLast = binding.root.width / 360 * 16
            decoration = ItemDecorationWithEnds(
                top = spaceVertical,
                topLast = spaceVertical,
                bottomLast = spaceVerticaLast,
                lastPredicate = { pos, count -> if (count % 2 == 0) pos == count - 1 || pos == count - 2 else pos == count - 1 }
            )
            binding.recyclerAnimation.addItemDecoration(decoration)
            binding.recyclerImage.addItemDecoration(decoration)
        }
        viewModel.addImage.observe(this) {
            activityAs<MainActivity>().askStoragePermissions { res ->
                if (res) activityAs<MainActivity>().pickImage {
                    viewModel.addCustomAnimation(it.uri, requireContext())
                }
            }
        }
        viewModel.showPreview.observe(this) {
            startActivity(PreviewActivity.getIntent(requireContext(), it))
        }
        binding.drawerHitBox.setOnClickListener {}
        binding.buttonMenu.setOnClickListener { openDrawer() }
        binding.buttonMenuBack.setOnClickListener { closeDrawer() }
        binding.buttonShare.setOnClickListener(::shareApp)
        binding.buttonRateUs.setOnClickListener(::openPlayMarketPage)
        binding.buttonPrivacyPolicy.setOnClickListener(::openPlayMarketPage)
        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
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
            cs.connect(R.id.drawer, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            cs.clear(R.id.drawer, ConstraintSet.END)
            TransitionManager.beginDelayedTransition(binding.drawerContainer, ChangeBounds().setDuration(100))
            cs.applyTo(binding.drawerContainer)
        }.start()
    }

    private fun closeDrawer() {
        val cs = ConstraintSet()
        cs.clone(binding.drawerContainer)
        cs.connect(R.id.drawer, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.START)
        cs.clear(R.id.drawer, ConstraintSet.START)
        val transition = ChangeBounds().setDuration(100).addListener(object : Transition.TransitionListener {
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

    override fun provideViewModel() = viewModel

    fun onBackPressed() {
        if (binding.drawerContainer.visibility == View.VISIBLE)
            closeDrawer()
        else
            requireActivity().finish()
    }

    private fun openPlayMarketPage() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(APP_LINK)))
    }

    private fun shareApp() {
        @Suppress("DEPRECATION")
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType("text/plain")
            .setText("Install me\n$APP_LINK")
            .createChooserIntent()
            .let(::startActivity)
    }

}