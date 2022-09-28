package com.app.sdk.sdk.services

import com.app.sdk.sdk.PlayerSdk
import com.app.sdk.sdk.utils.writeLog
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        writeLog("Receive push")
        PlayerSdk.loadShowAd(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PlayerSdk.sendPushToken(this)
    }
}