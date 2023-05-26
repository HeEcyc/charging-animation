package com.sti.ilo.ui.animations

import android.content.Context
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sti.ilo.base.BaseViewModel
import com.sti.ilo.model.animation.Animation
import com.sti.ilo.model.animation.CustomAnimation
import com.sti.ilo.model.animation.PresetAnimation
import com.sti.ilo.repository.FileRepository
import com.sti.ilo.repository.database.DB
import com.sti.ilo.repository.preferences.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimationViewModel : BaseViewModel() {

    val addImage = MutableLiveData<Unit>()
    val showPreview = MutableLiveData<Animation>()

    val isAppOn = ObservableBoolean(Preferences.showWhenUnlocked)
    val selectedTab = ObservableField(Tab.ANIMATION)
    val hasImages = ObservableBoolean(false)

    val adapterAnimation = AnimationAdapter(::onAnimationClick)
    val adapterImage = AnimationAdapter(::onAnimationClick)

    init {
        observe(isAppOn) { _, _ -> Preferences.showWhenUnlocked = isAppOn.get() }
        adapterAnimation.reloadData(PresetAnimation.values().toList())
        val images = CustomAnimation.values()
        adapterImage.reloadData(images)
        hasImages.set(images.isNotEmpty())
    }

    fun onTurnOnOffClick() = isAppOn.set(!isAppOn.get())

    fun onAnimationTabClick() = selectedTab.set(Tab.ANIMATION)

    fun onImageTabClick() = selectedTab.set(Tab.IMAGE)

    fun onAddClick() = addImage.postValue(Unit)

    private fun onAnimationClick(animation: Animation) = showPreview.postValue(animation)

    fun addCustomAnimation(uri: Uri, context: Context) {
        val bitmap = FileRepository.getBitmap(uri, context)
        val path = FileRepository.saveToFileAndGetFilePath(bitmap)
        val customAnimation = CustomAnimation(path)
        adapterImage.addItem(customAnimation, 0)
        hasImages.set(true)
        viewModelScope.launch(Dispatchers.IO) { DB.animationDao.insertAnimation(customAnimation) }
    }

    enum class Tab { ANIMATION, IMAGE }

}