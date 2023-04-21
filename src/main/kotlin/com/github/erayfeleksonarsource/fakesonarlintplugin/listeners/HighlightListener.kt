package com.github.erayfeleksonarsource.fakesonarlintplugin.listeners

import com.intellij.util.messages.Topic

class HighlightListener {

    companion object {
        interface HighlightNotifier {
            fun notifyCount(count: Int)
        }

        public val HIGHLIGHT_TOPIC = Topic.create("highlightTopic", HighlightNotifier::class.java)
    }

}