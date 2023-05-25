package com.kapi.ca.ui.preview

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.kapi.ca.R
import com.kapi.ca.base.BaseActivity
import com.kapi.ca.databinding.PreviewActivityBinding
import com.kapi.ca.model.animation.Animation
import com.kapi.ca.repository.preferences.Preferences

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
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            finish()
        }
    }

}