package com.github.erayfeleksonarsource.fakesonarlintplugin.services

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.slf4j.LoggerFactory

class NotificationService : AnAction() {
    val logger = LoggerFactory.getLogger(NotificationService::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        val notification = Notification("Custom Notification Group", "Hello world", NotificationType.INFORMATION)

        notification.notify(e.project)
    }
}
