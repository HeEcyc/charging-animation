package com.funny.charging_app.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.animation.LinearInterpolator
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter("adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, a: RecyclerView.Adapter<*>) {
    rv.adapter = a
}

@SuppressLint("Recycle")
@BindingAdapter("progress")
fun setProgress(lav: LottieAnimationView, s: String) {
    ObjectAnimator.ofFloat(lav.progress, s.dropLast(1).toInt().div(100f)).apply {
        interpolator = LinearInterpolator()
        duration = 1000
        addUpdateListener { lav.progress = it.animatedValue as Float }
        start()
    }
}