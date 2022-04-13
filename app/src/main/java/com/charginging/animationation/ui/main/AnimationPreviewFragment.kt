package com.charginging.animationation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.charginging.animationation.R
import com.charginging.animationation.model.AnimationItem
import com.charginging.animationation.ui.custom.AnimationHolderView

class AnimationPreviewFragment : Fragment() {

    companion object {
        private const val ANIMATION_ITEM = "animation_item"

        fun newInstance(animationItem: AnimationItem) = AnimationPreviewFragment()
            .apply { arguments = bundleOf(ANIMATION_ITEM to animationItem) }
    }

    override fun onCreateView(infl: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return infl.inflate(R.layout.app_animation_preview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(view.findViewById<AnimationHolderView>(R.id.holder)) {
            showView(requireArguments().getSerializable(ANIMATION_ITEM) as AnimationItem, true)
        }
    }

    fun startAnimation() {
        view?.findViewById<AnimationHolderView>(R.id.holder)?.playAnimation()
    }

    fun stopAnimation() {
        view?.findViewById<AnimationHolderView>(R.id.holder)?.pauseAnimation()
    }

}