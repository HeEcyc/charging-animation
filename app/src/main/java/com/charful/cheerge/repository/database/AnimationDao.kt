package com.charful.cheerge.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.charful.cheerge.model.animation.CustomAnimation

@Dao
interface AnimationDao {

    @Insert
    fun insertAnimation(customAnimation: CustomAnimation)

    @Query("SELECT * FROM custom_animations")
    fun selectAllAnimations(): List<CustomAnimation>

}