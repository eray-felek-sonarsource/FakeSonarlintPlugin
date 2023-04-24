package com.github.erayfeleksonarsource.fakesonarlintplugin

import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HighlightListener

class HighlightListenerAccumulator : HighlightListener {
    var receivedCount : MutableList<Int> = ArrayList()

    override fun notifyCount(count: Int) {
        receivedCount.add(count)
    }
}