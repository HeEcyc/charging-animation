package com.kapi.ca.ui.animations

import android.view.View
import androidx.activity.viewModels
import com.kapi.ca.R
import com.kapi.ca.base.BaseActivity
import com.kapi.ca.databinding.AnimationsActivityBinding
import com.kapi.ca.ui.custom.ItemDecorationWithEnds
import com.kapi.ca.ui.preview.PreviewActivity

class AnimationsActivity : BaseActivity<AnimationViewModel, AnimationsActivityBinding>() {

    val viewModel: AnimationViewModel by viewModels()

    override fun setupUI() {
        binding.root.post {
            val spaceInner = binding.root.width / 360 * 5
            val spaceOuter = binding.root.width / 360 * 23
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
            val spaceVertical = binding.root.width / 360 * 10
            val spaceVerticaLast = binding.root.width / 360 * 10
            decoration = ItemDecorationWithEnds(
                bottom = spaceVertical,
                bottomLast = spaceVerticaLast,
                lastPredicate = { pos, count -> if (count % 2 == 0) pos == count - 1 || pos == count - 2 else pos == count - 1 }
            )
            binding.recyclerAnimation.addItemDecoration(decoration)
        }
        viewModel.showPreview.observe(this) {
            startActivity(PreviewActivity.getIntent(this, it))
        }
        binding.buttonBack.setOnClickListener { finish() }
    }

    override fun provideViewModel() = viewModel

    override fun provideLayoutId() = R.layout.animations_activity

}