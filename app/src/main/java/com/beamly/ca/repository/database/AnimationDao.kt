package com.beamly.ca.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.beamly.ca.model.animation.CustomAnimation

@Dao
interface AnimationDao {

    @Insert
    fun insertAnimation(customAnimation: CustomAnimation)

    @Query("SELECT * FROM custom_animations")
    fun selectAllAnimations(): List<CustomAnimation>

}