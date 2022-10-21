package com.funnychar.ginganimation.utils

import android.content.Context
import androidx.databinding.Observable
import com.funnychar.ginganimation.base.FBaseFBroadcastFReceiverF

fun Observable.addOnPropertyChangedCallback(
    callback: (Observable, Int) -> Unit
): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) =
            callback(sender, propertyId)
    }.also { addOnPropertyChangedCallback(it) }

fun Context.registerReceiver(bbr: FBaseFBroadcastFReceiverF) =
    registerReceiver(bbr, bbr.provideIntentFilter())