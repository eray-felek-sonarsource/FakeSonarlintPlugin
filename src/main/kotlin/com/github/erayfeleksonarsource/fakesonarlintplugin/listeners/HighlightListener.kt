package com.github.erayfeleksonarsource.fakesonarlintplugin.listeners

import com.intellij.util.messages.Topic

interface HighlightListener {
    fun notifyCount(count: Int)

}



public val HIGHLIGHT_TOPIC = Topic.create("highlightTopic", HighlightListener::class.java)