package com.funnychar.ginganimation.base

import android.content.BroadcastReceiver
import android.content.IntentFilter

abstract class FBaseFBroadcastFReceiverF : BroadcastReceiver() {

    open fun provideIntentFilter(): IntentFilter? = null

}