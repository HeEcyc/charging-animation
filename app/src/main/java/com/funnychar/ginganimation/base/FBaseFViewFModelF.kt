package com.funnychar.ginganimation.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.funnychar.ginganimation.utils.addOnPropertyChangedCallback

abstract class FBaseFViewFModelF : ViewModel() {

    private val observablesAndObservers = mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    override fun onCleared() {
        System.currentTimeMillis()
        observablesAndObservers.forEach { (observable, observer) ->
            System.currentTimeMillis()
            observable.removeOnPropertyChangedCallback(observer)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        observablesAndObservers.clear()
        System.currentTimeMillis()
        super.onCleared()
        System.currentTimeMillis()
    }

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

}