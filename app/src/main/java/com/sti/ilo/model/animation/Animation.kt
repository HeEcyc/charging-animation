package com.sti.ilo.model.animation

import android.content.Context
import android.view.View
import java.io.Serializable

sealed interface Animation : Serializable {

    companion object {
        fun valueOf(s: String?) : Animation {
            return PresetAnimation.values().firstOrNull { it.name == s }
                ?: CustomAnimation.values().firstOrNull { it.filePath == s }
                ?: PresetAnimation.values().first()
        }
    }

    fun inflateAnimationView(context: Context): View

    fun getPrefString(): String

}