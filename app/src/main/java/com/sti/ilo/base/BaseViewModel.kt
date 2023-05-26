package com.sti.ilo.base

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sti.ilo.utils.addOnPropertyChangedCallback

abstract class BaseViewModel : ViewModel() {
    var isLocked = MutableLiveData<Boolean>()

    private val observablesAndObservers =
        mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    override fun onCleared() {
        observablesAndObservers.forEach { (observable, observer) ->
            observable.removeOnPropertyChangedCallback(observer)
        }
        observablesAndObservers.clear()
        super.onCleared()
    }

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

    fun lock() {
        isLocked.postValue(true)
    }

}