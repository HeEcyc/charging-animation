package com.sti.ilo.repository.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sti.ilo.App
import com.sti.ilo.model.animation.CustomAnimation

@Database(entities = [CustomAnimation::class], version = 1)
abstract class DB : RoomDatabase() {

    companion object {
        val animationDao by lazy {
            Room.databaseBuilder(App.instance, DB::class.java, "themesDB").build().getAnimationDao()
        }
    }

    abstract fun getAnimationDao(): AnimationDao

}