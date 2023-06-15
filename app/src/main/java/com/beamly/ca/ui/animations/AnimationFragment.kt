package com.beamly.ca.ui.animations

import android.view.View
import androidx.fragment.app.viewModels
import com.app.sdk.sdk.PremiumUserSdk
import com.beamly.ca.R
import com.beamly.ca.base.BaseFragment
import com.beamly.ca.databinding.AnimationFragmentBinding
import com.beamly.ca.ui.custom.ItemDecorationWithEnds
import com.beamly.ca.ui.preview.PreviewActivity

class AnimationFragment :
    BaseFragment<AnimationViewModel, AnimationFragmentBinding>(R.layout.animation_fragment) {

    val viewModel: AnimationViewModel by viewModels()

    override fun setupUI() {
        binding.root.post {
            val spaceInner = binding.root.width / 360 * 8
            val spaceOuter = binding.root.width / 360 * 16
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
            val spaceVertical = binding.root.width / 360 * 16
            val spaceVerticaLast = binding.root.width / 360 * 80
            decoration = ItemDecorationWithEnds(
                bottom = spaceVertical,
                bottomLast = spaceVerticaLast,
                lastPredicate = { pos, count -> if (count % 2 == 0) pos == count - 1 || pos == count - 2 else pos == count - 1 }
            )
            binding.recyclerAnimation.addItemDecoration(decoration)
        }
        viewModel.showPreview.observe(this) {
            PremiumUserSdk.showInAppAd(requireActivity()) {
                startActivity(PreviewActivity.getIntent(requireContext(), it))
            }
        }
    }

    override fun provideViewModel() = viewModel

}