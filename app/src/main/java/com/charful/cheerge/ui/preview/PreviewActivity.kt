package com.charful.cheerge.ui.preview

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.charful.cheerge.R
import com.charful.cheerge.base.BaseActivity
import com.charful.cheerge.databinding.PreviewActivityBinding
import com.charful.cheerge.model.animation.Animation
import com.charful.cheerge.repository.preferences.Preferences

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
        binding.animationHost1.addView(
            animation.inflateAnimationView(this),
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        binding.animationHost2.addView(
            animation.inflateAnimationView(this),
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        binding.buttonBack.setOnClickListener { onBackPressed() }
        binding.buttonHide.setOnClickListener {
            binding.buttonBack.visibility = View.GONE
            binding.buttonHide.visibility = View.GONE
            binding.buttonShow.visibility = View.VISIBLE
            binding.animationHost2.visibility = View.VISIBLE
        }
        binding.root.setOnClickListener {
            binding.buttonBack.visibility = View.VISIBLE
            binding.buttonHide.visibility = View.VISIBLE
            binding.buttonShow.visibility = View.GONE
            binding.animationHost2.visibility = View.GONE
        }
        binding.buttonApply.setOnClickListener {
            Preferences.selectedAnimation = animation
            AppliedDialog().apply {
                onButtonClick = this@PreviewActivity::finish
                show(supportFragmentManager, null)
            }
        }
    }

}