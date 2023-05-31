package com.bio.oiq.ui.preview

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.postDelayed
import com.bio.oiq.R
import com.bio.oiq.base.BaseActivity
import com.bio.oiq.base.BaseViewModel
import com.bio.oiq.databinding.PreviewActivityBinding
import com.bio.oiq.model.animation.Animation
import com.bio.oiq.repository.preferences.Preferences

class PreviewActivity : BaseActivity<BaseViewModel, PreviewActivityBinding>() {

    private val animation: Animation? by lazy { intent?.getSerializableExtra(EXTRAS_ANIMATION) as? Animation }

    companion object {
        private const val EXTRAS_ANIMATION = "extras_animation"

        fun getIntent(context: Context, animation: Animation) =
            Intent(context, PreviewActivity::class.java).apply {
                putExtra(EXTRAS_ANIMATION, animation)
            }
    }

    override fun provideLayoutId() = R.layout.preview_activity

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
        val animation = animation
        if (animation === null) return
        binding.layoutBig.setOnClickListener {}
        binding.animationContainerSmall.addView(animation.inflateAnimationView(this))
        binding.animationContainerBig.addView(animation.inflateAnimationView(this))
        binding.buttonExpand.setOnClickListener { binding.layoutBig.visibility = View.VISIBLE }
        binding.buttonCollapse.setOnClickListener { binding.layoutBig.visibility = View.GONE }
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            binding.applied.visibility = View.VISIBLE
            it.postDelayed(2000, ::finish)
        }
    }

}