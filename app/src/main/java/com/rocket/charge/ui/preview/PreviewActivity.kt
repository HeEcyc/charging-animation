package com.rocket.charge.ui.preview

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.rocket.charge.R
import com.rocket.charge.base.BaseActivity
import com.rocket.charge.databinding.PreviewActivityBinding
import com.rocket.charge.model.animation.Animation
import com.rocket.charge.repository.preferences.Preferences

class PreviewActivity : BaseActivity<PreviewViewModel, PreviewActivityBinding>() {

    val viewModel: PreviewViewModel by viewModels()

    companion object {
        private const val EXTRAS_ANIMATION = "extras_animation"

        fun getIntent(context: Context, animation: Animation) =
            Intent(context, PreviewActivity::class.java).putExtra(EXTRAS_ANIMATION, animation)
    }

    override fun provideLayoutId() = R.layout.preview_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        val animation = intent
            ?.getSerializableExtra(EXTRAS_ANIMATION)
            ?.let { it as? Animation }
        if (animation === null) {
            onBackPressed()
            return
        }
        binding.animationHost.addView(
            animation.inflateAnimationView(this),
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        binding.buttonBack.setOnClickListener { onBackPressed() }
        binding.buttonHide.setOnClickListener { binding.buttons.visibility = View.GONE }
        binding.root.setOnClickListener { binding.buttons.visibility = View.VISIBLE }
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            onBackPressed()
        }
    }

}