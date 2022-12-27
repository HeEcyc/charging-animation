package com.dec.ces.ui.main

import android.content.Context
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.dec.ces.base.BaseViewModel
import com.dec.ces.model.animation.Animation
import com.dec.ces.model.animation.CustomAnimation
import com.dec.ces.model.animation.PresetAnimation
import com.dec.ces.repository.FileRepository
import com.dec.ces.repository.database.DB
import com.dec.ces.repository.preferences.Preferences
import com.dec.ces.ui.adapters.AnimationAdapter
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