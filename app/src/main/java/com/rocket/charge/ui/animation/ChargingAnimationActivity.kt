package com.rocket.charge.ui.animation

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.rocket.charge.R
import com.rocket.charge.model.ClosableWindow
import com.rocket.charge.model.ClosableWindows
import com.rocket.charge.ui.custom.AnimationHolderView

class ChargingAnimationActivity : AppCompatActivity(), ClosableWindow {

    private var animationHolderView: AnimationHolderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.charging_animation_activity)
        animationHolderView = findViewById(R.id.root)
        animationHolderView?.setOnClickListener { closeWindow() }
        ClosableWindows.add(this)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        closeWindow()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeWindow()
    }

    private fun closeWindow() = ClosableWindows.close(this)

    override fun close() = finishAndRemoveTask()

}