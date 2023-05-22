package com.kapi.ca.model.animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.kapi.ca.App
import com.kapi.ca.BR
import com.kapi.ca.databinding.ACustomBinding
import com.kapi.ca.repository.background.display.BatteryLevelReceiver
import com.kapi.ca.repository.database.DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Entity(tableName = "custom_animations")
data class CustomAnimation (
    @PrimaryKey
    val filePath: String
) : Animation {

    companion object {
        fun values() = runBlocking(Dispatchers.IO) { DB.animationDao.selectAllAnimations() }
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