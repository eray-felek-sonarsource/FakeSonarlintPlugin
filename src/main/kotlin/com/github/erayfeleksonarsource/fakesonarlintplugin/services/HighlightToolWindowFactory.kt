package com.github.erayfeleksonarsource.fakesonarlintplugin.services

import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HighlightListener
import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HighlightListener.Companion.HIGHLIGHT_TOPIC
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import org.jetbrains.annotations.NotNull
import java.awt.BorderLayout
import java.util.*
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel


class HighlightToolWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val toolWindowContent = HighlightToolWindowContent(project)
        val content: Content = ContentFactory.SERVICE.getInstance().createContent(toolWindowContent.contentPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    private class HighlightToolWindowContent(project: Project) : HighlightListener.Companion.HighlightNotifier {
        val contentPanel = JPanel()
        private val highlightCount = JLabel()

        init {
            val connection = project.messageBus.connect()
            connection.subscribe(HIGHLIGHT_TOPIC, this)

            contentPanel.layout = BorderLayout(0, 20)
            contentPanel.border = BorderFactory.createEmptyBorder(40, 0, 0, 0)
            contentPanel.add(createHighlightPanel(), BorderLayout.PAGE_START)
            contentPanel.add(JPanel(), BorderLayout.CENTER)
            highlightCount.text = "highlight value"
        }

        @NotNull
        private fun createHighlightPanel(): JPanel {
            val highlightPanel = JPanel()
            setIconLabel(highlightCount, HIGHLIGHT_ICON_PATH)
            highlightPanel.add(highlightCount)
            return highlightPanel
        }

        private fun setIconLabel(label: JLabel, imagePath: String) {
            label.icon = ImageIcon(Objects.requireNonNull(javaClass.getResource(imagePath)))
        }

        companion object {
            private const val HIGHLIGHT_ICON_PATH = "/toolWindow/Icon.PNG"
        }

        override fun notifyCount(count: Int) {
            highlightCount.text = count.toString()
        }
    }
}