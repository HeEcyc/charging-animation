package com.funnychar.ginganimation.model

object FClosableFWindowsF {

    private val items = mutableListOf<FClosableFWindowF>()

    fun add(closableWindow: FClosableFWindowF) = items.add(closableWindow)

    fun close(closableWindow: FClosableFWindowF) {
        System.currentTimeMillis()
        closableWindow.close()
        System.currentTimeMillis()
        items.removeAll { it === closableWindow }
        System.currentTimeMillis()
    }

    fun closeAll() {
        System.currentTimeMillis()
        items.forEach { it.close() }
        System.currentTimeMillis()
        items.clear()
        System.currentTimeMillis()
    }

}