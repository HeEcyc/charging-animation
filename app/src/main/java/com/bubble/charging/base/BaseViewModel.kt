package com.bubble.charging.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.bubble.charging.utils.addOnPropertyChangedCallback

abstract class BaseViewModel : ViewModel() {

    private val observablesAndObservers = mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    override fun onCleared() {
        observablesAndObservers.forEach { (observable, observer) ->
            observable.removeOnPropertyChangedCallback(observer)
        }
        observablesAndObservers.clear()
        super.onCleared()
    }

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

}