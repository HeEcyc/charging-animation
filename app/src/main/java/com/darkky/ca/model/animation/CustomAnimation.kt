package com.darkky.ca.model.animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.darkky.ca.App
import com.darkky.ca.BR
import com.darkky.ca.databinding.ACustomBinding
import com.darkky.ca.repository.background.display.BatteryLevelReceiver
import com.darkky.ca.repository.database.DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Entity(tableName = "custom_animations")
data class CustomAnimation (
    @PrimaryKey
    val filePath: String
) : Animation {

    companion object {
        fun values() = runBlocking(Dispatchers.IO) {
            DB.animationDao.selectAllAnimations().toTypedArray()
        }
    }

    override fun inflateAnimationView(context: Context): View = ACustomBinding
        .inflate(LayoutInflater.from(context), null, false)
        .apply {
            setVariable(BR.blHolder, BatteryLevelReceiver.BatteryLevelHolder())
            Glide.with(App.instance).load(filePath).into(image)
        }
        .root

    override fun getPrefString(): String = filePath

}
