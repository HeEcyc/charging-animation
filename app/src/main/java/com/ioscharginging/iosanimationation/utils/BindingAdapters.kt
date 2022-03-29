package com.ioscharginging.iosanimationation.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("layout_constraintHorizontal_bias")
fun setHorizontalBias(v: View, b: Float) {
    (v.layoutParams as ConstraintLayout.LayoutParams).horizontalBias = b
}

@BindingAdapter("adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, a: RecyclerView.Adapter<*>) {
    rv.adapter = a
}