package com.darkky.ca.ui.preview

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.postDelayed
import com.darkky.ca.R
import com.darkky.ca.base.BaseActivity
import com.darkky.ca.base.BaseViewModel
import com.darkky.ca.databinding.PreviewActivityBinding
import com.darkky.ca.model.animation.Animation
import com.darkky.ca.repository.preferences.Preferences

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
        binding.animationContainer.addView(animation.inflateAnimationView(this))
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            binding.applied.visibility = View.VISIBLE
            it.postDelayed(2000, ::finish)
        }
    }

}