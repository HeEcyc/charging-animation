package com.urbanlife.lifeurban.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.urbanlife.lifeurban.model.animation.CustomAnimation

@Dao
interface AnimationDao {

    @Insert
    fun insertAnimation(customAnimation: CustomAnimation)

    @Query("SELECT * FROM custom_animations")
    fun selectAllAnimations(): List<CustomAnimation>

}