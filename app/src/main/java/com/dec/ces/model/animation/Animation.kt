package com.dec.ces.model.animation

import android.content.Context
import android.view.View
import java.io.Serializable

sealed interface Animation : Serializable {

    companion object {

        object NewAnimation : Animation {
            override fun inflateAnimationView(context: Context) = View(context)
            override fun getPrefString() = "new animation"
        }

        fun valueOf(s: String?) : Animation {
            return PresetAnimation.values().firstOrNull { it.name == s }
                ?: CustomAnimation.values().firstOrNull { it.filePath == s }
                ?: PresetAnimation.values().first()
        }

    }

    fun inflateAnimationView(context: Context): View

    fun getPrefString(): String

}
