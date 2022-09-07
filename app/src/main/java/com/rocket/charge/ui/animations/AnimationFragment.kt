package com.rocket.charge.ui.animations

import android.view.View
import androidx.fragment.app.viewModels
import com.rocket.charge.R
import com.rocket.charge.base.BaseFragment
import com.rocket.charge.databinding.AnimationFragmentBinding
import com.rocket.charge.ui.custom.ItemDecorationWithEnds
import com.rocket.charge.ui.main.MainActivity
import com.rocket.charge.ui.preview.PreviewActivity

class AnimationFragment : BaseFragment<AnimationViewModel, AnimationFragmentBinding>(R.layout.animation_fragment) {

    val viewModel: AnimationViewModel by viewModels()

    override fun setupUI() {
        binding.root.post {
            val spaceVertical = binding.root.width / 360 * 5
            val spaceInner = binding.root.width / 360 * 5
            val spaceOuter = binding.root.width / 360 * 14
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val decoration = ItemDecorationWithEnds(
                topFirst = spaceVertical,
                top = spaceVertical,
                topLast = spaceVertical,
                bottomFirst = spaceVertical,
                bottom = spaceVertical,
                bottomLast = spaceVertical,
                leftFirst = if (isLTR) spaceOuter else spaceInner,
                leftLast = if (isLTR) spaceInner else spaceOuter,
                rightFirst = if (isLTR) spaceInner else spaceOuter,
                rightLast = if (isLTR) spaceOuter else spaceInner,
                firstPredicate = { pos -> pos % 2 == 0 },
                lastPredicate = { pos, _ -> pos % 2 == 1 }
            )
            binding.recyclerAnimation.addItemDecoration(decoration)
            binding.recyclerImage.addItemDecoration(decoration)
        }
        binding.buttonBack.setOnClickListener {
            activityAs<MainActivity>().viewModel.onHomeClick()
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
    }

    override fun provideViewModel() = viewModel

}