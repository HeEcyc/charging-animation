package com.bio.oiq.utils

import android.content.Context
import androidx.databinding.Observable
import com.bio.oiq.base.BaseBroadcastReceiver

fun Observable.addOnPropertyChangedCallback(
    callback: (Observable, Int) -> Unit
): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) =
            callback(sender, propertyId)
    }.also { addOnPropertyChangedCallback(it) }

fun Context.registerReceiver(bbr: BaseBroadcastReceiver) =
    registerReceiver(bbr, bbr.provideIntentFilter())