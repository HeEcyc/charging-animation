package com.sti.ilo.ui

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, a: RecyclerView.Adapter<*>) {
    rv.adapter = a
}

@BindingAdapter("font")
fun setFont(tv: TextView, fontRes: Int) {
    tv.typeface = ResourcesCompat.getFont(tv.context, fontRes)
}