package com.kapi.ca.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kapi.ca.ui.custom.CircleProgressView

@BindingAdapter("adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, a: RecyclerView.Adapter<*>) {
    rv.adapter = a
}

@BindingAdapter("progress")
fun setProgress(cpv: CircleProgressView, p: String) {
    cpv.progress = p.dropLast(1).toFloat()
}