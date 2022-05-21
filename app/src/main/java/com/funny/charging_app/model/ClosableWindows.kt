package com.funny.charging_app.model

object ClosableWindows {

    private val items = mutableListOf<ClosableWindow>()

    fun add(closableWindow: ClosableWindow) = items.add(closableWindow)

    fun close(closableWindow: ClosableWindow) {
        closableWindow.close()
        items.removeAll { it === closableWindow }
    }

    fun closeAll() {
        items.forEach { it.close() }
        items.clear()
    }

}