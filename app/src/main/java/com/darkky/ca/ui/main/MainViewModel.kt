package com.darkky.ca.ui.main

import android.content.Context
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.darkky.ca.base.BaseViewModel
import com.darkky.ca.model.animation.Animation
import com.darkky.ca.model.animation.CustomAnimation
import com.darkky.ca.model.animation.PresetAnimation
import com.darkky.ca.repository.FileRepository
import com.darkky.ca.repository.database.DB
import com.darkky.ca.repository.preferences.Preferences
import com.darkky.ca.ui.adapters.AnimationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    val adapter = AnimationAdapter()

    init {
        adapter.reloadData(listOf(
            Animation.Companion.NewAnimation,
            *CustomAnimation.values(),
            *PresetAnimation.values()
        ))
    }

    fun addCustomAnimation(uri: Uri, context: Context) {
        val bitmap = FileRepository.getBitmap(uri, context)
        val path = FileRepository.saveToFileAndGetFilePath(bitmap)
        val customAnimation = CustomAnimation(path)
        adapter.addItem(customAnimation, 1)
        viewModelScope.launch(Dispatchers.IO) { DB.animationDao.insertAnimation(customAnimation) }
    }

    val isAppOn = ObservableBoolean(Preferences.showWhenUnlocked)

    fun onTurnOnOffClick() = isAppOn.set(!isAppOn.get())

}