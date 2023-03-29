package com.juno.ca.ui.preview

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.postDelayed
import com.juno.ca.R
import com.juno.ca.base.BaseActivity
import com.juno.ca.base.BaseViewModel
import com.juno.ca.databinding.PreviewActivityBinding
import com.juno.ca.model.animation.Animation
import com.juno.ca.repository.preferences.Preferences

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
        binding.animationContainerSmall.addView(animation.inflateAnimationView(this))
        binding.animationContainerBig.addView(animation.inflateAnimationView(this))
        binding.animationContainerSmall.setOnClickListener { binding.animationContainerBig.visibility = View.VISIBLE }
        binding.animationContainerBig.setOnClickListener { binding.animationContainerBig.visibility = View.GONE }
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            binding.applied.visibility = View.VISIBLE
            it.postDelayed(2000, ::finish)
        }
    }

}