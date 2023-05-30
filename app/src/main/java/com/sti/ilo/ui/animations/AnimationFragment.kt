package com.sti.ilo.ui.animations

import androidx.fragment.app.viewModels
import com.sti.ilo.R
import com.sti.ilo.base.BaseFragment
import com.sti.ilo.databinding.AnimationFragmentBinding
import com.sti.ilo.model.animation.PresetAnimation
import com.sti.ilo.ui.preview.PreviewActivity

class AnimationFragment :
    BaseFragment<AnimationViewModel, AnimationFragmentBinding>(R.layout.animation_fragment) {

    val viewModel: AnimationViewModel by viewModels()

    override fun setupUI() {
        listOf(binding.a1, binding.a2, binding.a3, binding.a4, binding.a5, binding.a6, binding.a7).forEachIndexed { i, v ->
            binding.motionLayout.listeners[v] = {
                startActivity(PreviewActivity.getIntent(requireContext(), PresetAnimation.values()[i]))
            }
        }
    }

    override fun provideViewModel() = viewModel

}