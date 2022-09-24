package com.charful.cheerge.utils

import android.content.Context
import android.view.View
import androidx.databinding.Observable
import com.charful.cheerge.base.BaseBroadcastReceiver

fun Observable.addOnPropertyChangedCallback(
    callback: (Observable, Int) -> Unit
): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) =
            callback(sender, propertyId)
    }.also { addOnPropertyChangedCallback(it) }

fun Context.registerReceiver(bbr: BaseBroadcastReceiver) =
    registerReceiver(bbr, bbr.provideIntentFilter())

fun View.setOnClickListener(action: () -> Unit) = setOnClickListener { action() }