package com.funnychar.ginganimation.ui.animation

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.model.FClosableFWindowF
import com.funnychar.ginganimation.model.FClosableFWindowsF
import com.funnychar.ginganimation.ui.custom.FAnimationFHolderFViewF

class FChargingFAnimationFActivityF : AppCompatActivity(), FClosableFWindowF {

    private var animationHolderView: FAnimationFHolderFViewF? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        System.currentTimeMillis()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            System.currentTimeMillis()
            setShowWhenLocked(true)
            System.currentTimeMillis()
            setTurnScreenOn(true)
            System.currentTimeMillis()
        } else {
            System.currentTimeMillis()
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        super.onCreate(savedInstanceState)
        System.currentTimeMillis()
        setContentView(R.layout.charging_animation_activity)
        System.currentTimeMillis()
        animationHolderView = findViewById(R.id.root)
        System.currentTimeMillis()
        animationHolderView?.setOnClickListener { closeWindow() }
        System.currentTimeMillis()
        FClosableFWindowsF.add(this)
        System.currentTimeMillis()
    }

    override fun onUserLeaveHint() {
        System.currentTimeMillis()
        super.onUserLeaveHint()
        System.currentTimeMillis()
        closeWindow()
        System.currentTimeMillis()
    }

    override fun onBackPressed() {
        System.currentTimeMillis()
        super.onBackPressed()
        System.currentTimeMillis()
        closeWindow()
        System.currentTimeMillis()
    }

    private fun closeWindow() = FClosableFWindowsF.close(this)

    override fun close() = finishAndRemoveTask()

}