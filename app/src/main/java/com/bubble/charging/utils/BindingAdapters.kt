package com.bubble.charging.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, a: RecyclerView.Adapter<*>) {
    rv.adapter = a
}