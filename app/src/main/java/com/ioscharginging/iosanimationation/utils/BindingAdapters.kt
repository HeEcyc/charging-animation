package com.ioscharginging.iosanimationation.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.luongvo.widget.iosswitchview.SwitchView

@BindingAdapter("adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, a: RecyclerView.Adapter<*>) {
    rv.adapter = a
}

@BindingAdapter("checked")
fun setChecked(sv: SwitchView, b: Boolean) {
    sv.toggle(b)
}