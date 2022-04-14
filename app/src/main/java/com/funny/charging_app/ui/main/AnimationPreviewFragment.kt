package com.funny.charging_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.funny.charging_app.R
import com.funny.charging_app.model.AnimationItem
import com.funny.charging_app.ui.custom.AnimationHolderView

class AnimationPreviewFragment : Fragment() {

    private val appViewModel: AppActivity.AppViewModel by activityViewModels()

    companion object {
        private const val ANIMATION_ITEM = "animation_item"

        fun newInstance(animationItem: AnimationItem) = AnimationPreviewFragment()
            .apply { arguments = bundleOf(ANIMATION_ITEM to animationItem) }
    }

    override fun onCreateView(infl: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return infl.inflate(R.layout.app_animation_preview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animationItem = requireArguments().getSerializable(ANIMATION_ITEM) as AnimationItem
        with(view.findViewById<AnimationHolderView>(R.id.holder)) {
            isPreview = true
            showView(animationItem, true)
        }
        appViewModel.batteryLevel.observe(viewLifecycleOwner) { animationItem.setBatteryLevel(it) }
    }

    fun startAnimation() {
        view?.findViewById<AnimationHolderView>(R.id.holder)?.playAnimation()
    }

    fun stopAnimation() {
        view?.findViewById<AnimationHolderView>(R.id.holder)?.pauseAnimation()
    }

}